package com.schoolinformer.controller

import com.schoolinformer.model.dto.FeatureDTO
import com.schoolinformer.model.enums.DevelopmentStage
import com.schoolinformer.service.FeatureService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/feature")
class FeatureController(
    private val featureService: FeatureService
) {
    @PostMapping
    fun saveFeature(@RequestBody feature: FeatureDTO): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.CREATED).body(featureService.saveFeature(feature))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    @PatchMapping("/mark-as-done")
    fun markFeatureAsDone(@RequestParam featureId: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.CREATED).body(featureService.markFeatureAsDone(featureId))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    @PatchMapping("/change-development-state")
    fun moveFeature(
        @RequestParam featureId: Long,
        @RequestParam destinationStage: DevelopmentStage
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.CREATED).body(featureService.moveFeature(featureId, destinationStage))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    @GetMapping("/roadmap")
    fun getDevelopment(): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.OK).body(featureService.getRoadMap())
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    @GetMapping("/archived")
    fun getArchivedDevelopment(): ResponseEntity<Any> {
        return try {
            ResponseEntity.status(HttpStatus.OK).body(featureService.getArchivedDevelopment())
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }
}