package com.schoolinformer.repository

import com.schoolinformer.model.entity.Feature
import com.schoolinformer.model.enums.DevelopmentStage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeatureRepository : JpaRepository<Feature, Long> {
    fun findFeatureByDevelopmentStage(stage: DevelopmentStage): List<Feature>
}