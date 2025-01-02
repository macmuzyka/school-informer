package com.schoolinformer.model.dto

import com.schoolinformer.model.entity.Feature
import com.schoolinformer.model.enums.DevelopmentStage
import java.time.LocalDateTime

data class FeatureDisplayDTO(
    val id: Long,
    val developmentStage: DevelopmentStage,
    val description: String,
    val createdAt: LocalDateTime,
    val lastUpdatedAt: LocalDateTime
) {
    constructor(feature: Feature) : this(
        id = feature.id ?: 0L,
        developmentStage = feature.developmentStage,
        description = feature.description,
        createdAt = feature.createdAt ?: LocalDateTime.now(),
        lastUpdatedAt = feature.lastUpdatedAt ?: LocalDateTime.now()
    )
}