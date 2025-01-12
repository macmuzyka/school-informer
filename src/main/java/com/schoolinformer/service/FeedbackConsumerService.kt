package com.schoolinformer.service

import com.schoolinformer.model.entity.FeedbackProvider
import com.schoolinformer.model.dto.FeedbackDTO
import com.schoolinformer.model.entity.FeedbackContent
import com.schoolinformer.repository.FeedbackProviderRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class FeedbackConsumerService(
    private val feedbackProviderRepository: FeedbackProviderRepository
) {
    private val log = LoggerFactory.getLogger(FeedbackConsumerService::class.java)

    @KafkaListener(
        topics = ["feedback-supplier"],
        groupId = "feedback",
        containerFactory = "feedbackListenerContainerFactory"
    )
    fun consumeFeedback(feedbackDTO: FeedbackDTO) {
        log.info("[KAFKA LISTENER] -> received message in [feedback] group ID")
        log.info("Incoming object: $feedbackDTO")
        try {
            val provider = getProvider(feedbackDTO)
            val contentToSave = FeedbackContent(feedbackDTO.feedbackContent).apply { this.feedbackProvider = provider }
            provider.feedbacks.add(contentToSave)

            feedbackProviderRepository.save(provider)
        } catch (e: Exception) {
            log.error("e.message")
            log.error(e.message)
            e.printStackTrace()
        }
    }

    private fun getProvider(feedbackDTO: FeedbackDTO): FeedbackProvider {
        log.info("feedbackDTO: $feedbackDTO")
        return feedbackProviderRepository.findFeedbackProviderByProviderEmailEquals(feedbackDTO.email)
            .takeIf { it.isPresent }?.get()?.also { log.info("Existing provider found: $it") }
            ?: FeedbackProvider(feedbackDTO).also { log.info("Saved new feedback provider: $it") }
    }
}