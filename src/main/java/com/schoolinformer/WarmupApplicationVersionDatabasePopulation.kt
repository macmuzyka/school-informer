package com.schoolinformer

import com.schoolinformer.model.entity.Address
import com.schoolinformer.model.entity.ApplicationVersion
import com.schoolinformer.model.entity.PUser
import com.schoolinformer.repository.AddressRepository
import com.schoolinformer.repository.ApplicationVersionRepository
import com.schoolinformer.repository.PUserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class WarmupApplicationVersionDatabasePopulation(
    private val applicationVersionRepository: ApplicationVersionRepository,
    private val PUserRepository: PUserRepository,
    private val addressRepository: AddressRepository
) : ApplicationListener<ApplicationStartedEvent> {
    private val log = LoggerFactory.getLogger(WarmupApplicationVersionDatabasePopulation::class.java)

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
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

        val users = PUserRepository.saveAll(
            listOf(
                PUser("mac1", mutableListOf()),
                PUser("mac2", mutableListOf()),
                PUser("mac3", mutableListOf()),
                PUser("mac4", mutableListOf()),
                PUser("mac5", mutableListOf()),
                PUser("mac6", mutableListOf()),
                PUser("mac7", mutableListOf()),
                PUser("mac8", mutableListOf()),
                PUser("mac9", mutableListOf()),
                PUser("mac10", mutableListOf())
            )
        )

        for (user in users) {
            val addresses = addressRepository.saveAll(
                listOf(
                    Address("street1", "zip1", "city1"),
                    Address("street2", "zip2", "city2"),
                    Address("street3", "zip3", "city3"),
                    Address("street4", "zip4", "city4"),
                    Address("street5", "zip5", "city5")
                )
            )
            addresses.onEach { it.userId = user.id }
            user.addresses = addresses
            PUserRepository.save(user)
        }
    }
}