package com.schoolinformer.service

import com.schoolinformer.model.dto.ApplicationValidityDTO
import com.schoolinformer.model.enums.Validity
import com.schoolinformer.service.utils.KafkaUtils.Companion.getMessageSentViaKafkaCompletableFuture
import com.schoolinformer.service.utils.KafkaUtils.Companion.plain
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaMessageBrokerService(
    private val featureService: FeatureService,
    private val applicationVersionService: ApplicationVersionService,
    private val kafkaRoadmapTemplate: KafkaTemplate<String, Map<String, List<String>>>,
    private val kafkaApplicationValidityTemplate: KafkaTemplate<String, ApplicationValidityDTO>
) {
    private val log = LoggerFactory.getLogger(KafkaMessageBrokerService::class.java)

    fun sendCurrentRoadmapAndApplicationValidity(validity: Validity) {
        kafkaRoadmapTemplate.send(
            "roadmap-fetch", featureService.getRoadMap().plain()
        ).also {
            log.info("Roadmap to application requesting sent: {}", it.getMessageSentViaKafkaCompletableFuture())
        }
        kafkaApplicationValidityTemplate.send(
            "application-validity", ApplicationValidityDTO(
                validity = validity,
                latestVersion = applicationVersionService.getLatestApplicationVersion(),
                message = ""
            ).also {
                log.info("Information about latest version $it to deprecated application sent")
            }
        )
    }
}