package com.schoolinformer.service

import com.schoolinformer.model.dto.FeedbackGroupedByEmailDTO
import com.schoolinformer.repository.FeedbackProviderRepository
import com.schoolinformer.service.utils.QueryResultsMapper.Companion.buildFeedbackGroupedByEmail
import org.springframework.stereotype.Service

@Service
class FeedbackService(private val feedbackProviderRepository: FeedbackProviderRepository) {
    fun getAllFeedbackGroupedByProviders(): Map<String, List<FeedbackGroupedByEmailDTO>> {
        return feedbackProviderRepository
                .findFeedbackContentsGroupedByProviders()
                .map { buildFeedbackGroupedByEmail(it) }
                .groupBy { it.email }
    }
}