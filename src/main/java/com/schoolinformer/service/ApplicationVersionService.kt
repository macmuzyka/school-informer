package com.schoolinformer.service

import com.schoolinformer.model.entity.ApplicationVersion
import com.schoolinformer.repository.ApplicationVersionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ApplicationVersionService(
    private val applicationVersionRepository: ApplicationVersionRepository
) {
    private val log = LoggerFactory.getLogger(ApplicationVersionService::class.java)

    fun getLatestApplicationVersion(): String {
        return applicationVersionRepository.findLatestVersion().takeIf { it.isPresent }?.get()?.applicationVersion
            ?: "1.0.0"
    }

    fun saveApplicationVersion(incomingApplicationVersion: String) {
        applicationVersionRepository.save(
            ApplicationVersion(
                applicationVersion = incomingApplicationVersion,
                description = "Application version received from application-version"
            )
        ).also { log.info("Saved application version record: $it") }
    }
}