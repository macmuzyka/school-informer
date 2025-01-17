package com.schoolinformer

import com.schoolinformer.model.entity.ApplicationVersion
import com.schoolinformer.repository.ApplicationVersionRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class WarmupApplicationVersionDatabasePopulation(
    private val applicationVersionRepository: ApplicationVersionRepository
) : ApplicationListener<ApplicationReadyEvent> {
    private val log = LoggerFactory.getLogger(WarmupApplicationVersionDatabasePopulation::class.java)

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        log.info("Application version database warmup")
        applicationVersionRepository.findAll()
            .takeIf { it.isEmpty() }
            ?.let {
                applicationVersionRepository
                    .save(
                        ApplicationVersion(
                            applicationVersion = "1.0.0",
                            description = "Reference point created upon database warmup"
                        )
                    ).also { log.info("Application version as reference point upon database warmup created") }
            } ?: log.info("No application version database warmup needed")
    }
}