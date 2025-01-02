package com.schoolinformer.service.utils

import com.schoolinformer.model.dto.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class QueryResultsMapper {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        fun buildFeedbackGroupedByEmail(queryResult: Array<Any>): FeedbackGroupedByEmailDTO {
            val email = queryResult[0] as String
            val feedbacksFromQuery = queryResult[1] as Array<*>
            val feedbacks = feedbacksFromQuery.map { qr ->
                val (content, createdAt) = (qr as String).split("^")
                ContentDTO(
                    content = content,
                    createdAt = LocalDateTime.parse(
                        createdAt
                            .substring(0, 19)
                            .replace("T", " "), formatter
                    )
                )
            }
            return FeedbackGroupedByEmailDTO(email, feedbacks)
        }
    }
}