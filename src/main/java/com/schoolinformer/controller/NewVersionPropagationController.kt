package com.schoolinformer.controller

import com.schoolinformer.service.NewVersionFeaturesPropagationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/propagate")
class NewVersionPropagationController(
    private val newVersionFeaturesPropagationService: NewVersionFeaturesPropagationService
) {
    @GetMapping
    fun propagateNewVersionFeatures(): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.OK)
                .body(newVersionFeaturesPropagationService.propagateNewVersionFeaturesAmongFeedbackProviders())
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }
}