package com.schoolinformer.repository

import com.schoolinformer.model.dto.FeedbackDisplayDTO
import com.schoolinformer.model.entity.FeedbackProvider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FeedbackProviderRepository : JpaRepository<FeedbackProvider, Long> {

    fun findFeedbackProviderByProviderEmailEquals(email: String): Optional<FeedbackProvider>

    @Query(
        value = "SELECT fp.provider_email FROM feedback_provider fp ",
        nativeQuery = true
    )
    fun findAllFeedbackProviderEmails(): List<String>

    @Query(nativeQuery = true)
    fun findFeedbackContentWithProvider(): List<FeedbackDisplayDTO>
}