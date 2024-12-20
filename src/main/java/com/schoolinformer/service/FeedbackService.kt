package com.schoolinformer.service

import com.schoolinformer.model.dto.ContentDTO
import com.schoolinformer.repository.FeedbackProviderRepository
import com.schoolinformer.service.utils.QueryResultsMapper.Companion.buildFeedbackGroupedByEmail
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FeedbackService(private val feedbackProviderRepository: FeedbackProviderRepository) {
    private val log = LoggerFactory.getLogger(FeedbackService::class.java)

    fun getAllFeedbackGroupedByProviders(): Map<String, List<ContentDTO>> {
        return feedbackProviderRepository.findFeedbackContentsGroupedByProviders()
                .map { buildFeedbackGroupedByEmail(it) }
                .groupBy { it.email }
                .mapValues { (_, feedbackGrouped) ->
                    feedbackGrouped
                            .flatMap { it.feedback }
                }
    }
}