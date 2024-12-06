package com.schoolinformer.model.dto

data class FeedbackGroupedByEmailDTO(val email: String, val feedback: List<ContentDTO>)