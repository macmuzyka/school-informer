package com.schoolinformer.model.dto

import java.time.LocalDateTime

data class FeedbackDisplayDTO(
    val providerEmail: String = "",
    val feedbackContent: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
)