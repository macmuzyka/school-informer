package com.schoolinformer.repository

import com.schoolinformer.model.entity.FeedbackProvider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FeedbackProviderRepository : JpaRepository<FeedbackProvider, Long> {

    fun findFeedbackProviderByProviderEmailEquals(email: String): Optional<FeedbackProvider>

    @Query(
            value = "SELECT fp.provider_email AS provider, ARRAY_AGG(fc.feedback_content) AS feedbacks " +
                    "FROM feedback_provider fp " +
                    "JOIN feedback_content fc ON fp.id = fc.feedback_provider_id " +
                    "GROUP BY fp.provider_email, fc.feedback_content",
            nativeQuery = true
    )
    fun findFeedbackContentsGroupedByProviders(): List<Array<Any>>

}