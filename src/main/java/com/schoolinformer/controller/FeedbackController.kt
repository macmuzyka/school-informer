package com.schoolinformer.controller

import com.schoolinformer.service.FeedbackService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/feedback")
class FeedbackController(private val feedbackService: FeedbackService) {
    @GetMapping
    fun getAllFeedback(): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.OK).body(feedbackService.getAllFeedbackGroupedByProviders())
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }
}