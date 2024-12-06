package com.schoolinformer.repository

import com.schoolinformer.model.entity.FeedbackProvider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FeedbackProviderRepository : JpaRepository<FeedbackProvider, Long> {

    fun findFeedbackProviderByProviderEmailEquals(email: String): Optional<FeedbackProvider>

    @Query(value =
    "SELECT fp.provider_email AS email, " +
            "array_agg(concat(fc.feedback_content || '^' || fc.created_at)) as feedbacks " +
            "FROM feedback_provider fp " +
            "INNER JOIN feedback_content fc ON fp.id = fc.feedback_provider_id " +
            "GROUP BY fp.provider_email",
            nativeQuery = true
    )
    fun findFeedbackContentsGroupedByProviders(): List<Array<Any>>

}