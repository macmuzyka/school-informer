package com.schoolinformer.service.utils

import com.schoolinformer.model.dto.ContentDTO
import com.schoolinformer.model.dto.FeedbackGroupedByEmailDTO

class QueryResultsMapper {
    companion object {
        fun buildFeedbackGroupedByEmail(queryResult: Array<Any>): FeedbackGroupedByEmailDTO {
            val email = queryResult[0] as String
            val feedbacksFromQuery = queryResult[1] as Array<*>
            val feedbackList = feedbacksFromQuery.map { qr -> ContentDTO(qr as String)}
            return FeedbackGroupedByEmailDTO(email, feedbackList)
        }
    }
}