package com.schoolinformer.service

import com.schoolinformer.model.dto.FeatureDTO
import com.schoolinformer.model.dto.FeatureDisplayDTO
import com.schoolinformer.model.entity.Feature
import com.schoolinformer.model.enums.DevelopmentStage
import com.schoolinformer.repository.FeatureRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FeatureService(
    private val featureRepository: FeatureRepository
) {
    private val log = LoggerFactory.getLogger(FeatureService::class.java)

    fun saveFeature(feature: FeatureDTO): FeatureDTO {
        log.info("incoming feature: $feature")
        validateStage(feature.developmentStage)
        return FeatureDTO(featureRepository.save(Feature(feature)))
    }

    fun markFeatureAsDone(featureId: Long): FeatureDisplayDTO {
        return featureRepository.findById(featureId).takeIf { it.isPresent }?.get()?.let {
            it.apply { this.developmentStage = DevelopmentStage.DONE }
            FeatureDisplayDTO(featureRepository.save(it))
        } ?: throw IllegalArgumentException("Could not find feature with ID $featureId")
    }

    fun moveFeature(featureId: Long, stage: DevelopmentStage): FeatureDisplayDTO {
        validateStage(stage)
        return featureRepository.findById(featureId).takeIf { it.isPresent }?.get()?.let {
            it.apply { this.developmentStage = stage }
            FeatureDisplayDTO(featureRepository.save(it))
        } ?: throw IllegalArgumentException("Could not find feature with ID $featureId")
    }

    private fun validateStage(stage: DevelopmentStage) {
        if (stage !in DevelopmentStage.entries) {
            throw IllegalArgumentException("Passed $stage development stage is not valid!")
        }
    }

    fun getRoadMap(): Map<DevelopmentStage, List<FeatureDisplayDTO>> {
        return featureRepository.findAll()
            .groupedIntoMap()
            .filterKeys { it in DevelopmentStage.entries - DevelopmentStage.ARCHIVED_DONE }
    }

    fun getArchivedDevelopment(): Map<DevelopmentStage, List<FeatureDisplayDTO>> {
        return featureRepository.findAll()
            .groupedIntoMap()
            .filterKeys { it == DevelopmentStage.ARCHIVED_DONE }
    }

    private fun List<Feature>.groupedIntoMap(): Map<DevelopmentStage, List<FeatureDisplayDTO>> {
        return this
            .groupBy { it.developmentStage }
            .mapValues { (_, features) -> features.map { FeatureDisplayDTO(it) } }
    }

    fun getFeaturesDone(): List<FeatureDisplayDTO> {
        return featureRepository.findFeatureByDevelopmentStage(DevelopmentStage.DONE).map { FeatureDisplayDTO(it) }
    }

    fun archiveDoneFeatures(): List<FeatureDisplayDTO> {
        return featureRepository.findFeatureByDevelopmentStage(DevelopmentStage.DONE)
            .onEach { markFeatureAsArchived(it) }
            .map { FeatureDisplayDTO(it) }
    }

    private fun markFeatureAsArchived(feature: Feature) {
        feature.developmentStage = DevelopmentStage.ARCHIVED_DONE
        featureRepository.save(feature)
            .also { log.info("Feature ${FeatureDisplayDTO(it)} archived") }
    }
}