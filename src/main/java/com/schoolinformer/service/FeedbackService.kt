package com.schoolinformer.service

import com.schoolinformer.model.dto.ContentDTO
import com.schoolinformer.repository.FeedbackProviderRepository
import org.springframework.stereotype.Service

@Service
class FeedbackService(private val feedbackProviderRepository: FeedbackProviderRepository) {
    fun getAllFeedbackGroupedByProviders(): Map<String, List<ContentDTO>> {
        return feedbackProviderRepository.findFeedbackContentWithProvider()
            .groupBy { it.providerEmail }
            .mapValues { (_, contents) ->
                contents.map { ContentDTO(it.feedbackContent, it.createdAt) }
            }
    }
}