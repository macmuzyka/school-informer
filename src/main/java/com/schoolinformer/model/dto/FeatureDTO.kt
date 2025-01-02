package com.schoolinformer.model.dto

import com.schoolinformer.model.entity.Feature
import com.schoolinformer.model.enums.DevelopmentStage

data class FeatureDTO(
    val description: String = "",
    val developmentStage: DevelopmentStage = DevelopmentStage.NOT_YET_DETERMINED
) {
    constructor(feature: Feature) : this(
        description = feature.description,
        developmentStage = feature.developmentStage
    )
}