package com.schoolinformer.service

import com.schoolinformer.model.enums.Validity
import com.schoolinformer.repository.ApplicationVersionRepository
import com.schoolinformer.service.utils.VersionComparator.Companion.compareVersion
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ApplicationVersionFetchConsumer(
    private val applicationVersionRepository: ApplicationVersionRepository,
    private val applicationVersionService: ApplicationVersionService,
    private val kafkaMessageBrokerService: KafkaMessageBrokerService
) {
    private val log = LoggerFactory.getLogger(ApplicationVersionFetchConsumer::class.java)

    @KafkaListener(
        topics = ["application-version"],
        groupId = "application-version",
        containerFactory = "applicationVersionFetchListenerContainerFactory"
    )
    fun consumeApplicationVersionFetch(incomingApplicationVersion: String) {
        log.info("[KAFKA LISTENER] => incoming application version : {}", incomingApplicationVersion)
        val versionCompareResult = compareVersion(applicationVersionService.getLatestApplicationVersion(), incomingApplicationVersion)

        versionCompareResult.takeIf { it == 1 }?.run { saveNewApplicationVersion(incomingApplicationVersion) }
        sendCurrentRoadmapAndApplicationValidityViaKafka(versionCompareResult)
    }

    private fun saveNewApplicationVersion(incomingApplicationVersion: String) {
        applicationVersionService.saveApplicationVersion(incomingApplicationVersion)
    }

    private fun sendCurrentRoadmapAndApplicationValidityViaKafka(versionCompareResult: Int) {
        kafkaMessageBrokerService.sendCurrentRoadmapAndApplicationValidity(resolveValidity(versionCompareResult))
    }

    private fun resolveValidity(versionCompareResult: Int): Validity {
        return when (versionCompareResult) {
            0, 1 -> Validity.UP_TO_DATE
            -1 -> Validity.DEPRECATED
            else -> Validity.NOT_DETERMINED_YET
        }
    }
}