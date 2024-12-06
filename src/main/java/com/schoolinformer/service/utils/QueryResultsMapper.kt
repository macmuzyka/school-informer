package com.schoolinformer.service.utils

import com.schoolinformer.model.dto.ContentDTO
import com.schoolinformer.model.dto.FeedbackGroupedByEmailDTO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class QueryResultsMapper {
    companion object {
        fun buildFeedbackGroupedByEmail(queryResult: Array<Any>): FeedbackGroupedByEmailDTO {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            val email = queryResult[0] as String
            val feedbacksFromQuery = queryResult[1] as Array<*>
            val feedbackList = feedbacksFromQuery.map { qr ->
                val (content, createdAt) = (qr as String).split("^")
                ContentDTO(content = content, createdAt = LocalDateTime.parse(createdAt, formatter))
            }
            return FeedbackGroupedByEmailDTO(email, feedbackList)
        }
    }
}