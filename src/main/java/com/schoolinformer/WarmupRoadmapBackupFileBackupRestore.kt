package com.schoolinformer

import com.schoolinformer.config.ApplicationConfig
import com.schoolinformer.model.entity.Feature
import com.schoolinformer.model.enums.DevelopmentStage
import com.schoolinformer.repository.FeatureRepository
import com.schoolinformer.service.utils.FileUtils.Companion.fileExists
import com.schoolinformer.service.utils.FileUtils.Companion.getDirectory
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class WarmupRoadmapBackupFileBackupRestore(
    private val featureRepository: FeatureRepository,
    private val applicationConfig: ApplicationConfig
) : ApplicationListener<ApplicationStartedEvent> {
    private val log = LoggerFactory.getLogger(WarmupRoadmapBackupFileBackupRestore::class.java)

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        return try {
            noFeaturesInDatabase()?.run {
                restoreRoadmapFromFile()
            } ?: updateBackupFile()
        } catch (fnf: FileNotFoundException) {
            log.warn(fnf.message)
        } catch (e: Exception) {
            log.error(e.message)
        }
    }

    private fun noFeaturesInDatabase(): List<Feature>? {
        return featureRepository.findAll().takeIf { it.isEmpty() }
    }

    private fun restoreRoadmapFromFile() {
        val backupFilePath = getFullFilePath()
        fileExists(backupFilePath)?.let { file ->
            val properties = Properties().apply { load(file.inputStream()) }
            val roadmap = buildRoadmap(properties)
            saveToDatabase(roadmap)
        } ?: throw FileNotFoundException("Roadmap backup file not found!")
    }

    private fun getFullFilePath(): String {
        val directory = getDirectory(applicationConfig.roadmapBackupDirectory)
        return directory.absolutePath + File.separator + applicationConfig.roadmapBackupFileName
    }

    private fun buildRoadmap(properties: Properties): Map<DevelopmentStage, List<String>> {
        return properties.entries.associate { (key, values) ->
            DevelopmentStage.valueOf(key.toString()) to values.toString().split("^")
        }.also { log.info("Resolved roadmap from backup file: $it") }
    }

    private fun saveToDatabase(roadmap: Map<DevelopmentStage, List<String>>) {
        roadmap.entries.onEach { stage ->
            stage.value.onEach { featureWithOriginalCreatedDate ->
                val (feature, originalCreatedDate) = featureWithOriginalCreatedDate.split("@")
                featureRepository.save(Feature(feature, stage.key, LocalDateTime.parse(originalCreatedDate)))
            }
        }
    }

    private fun updateBackupFile() {
        val backupFilePath = getFullFilePath()
        fileExists(backupFilePath)?.let { file ->
            val properties = Properties()
            val existingRoadmap = featureRepository.findAll().groupBy {
                it.developmentStage.name
            }.mapValues { (_, features) -> features.joinToString("^") { it.description + "@" + it.createdAt } }
            existingRoadmap.forEach { (stage, features) ->
                properties[stage] = features
            }
            properties.store(
                file.outputStream(), null
            )
            log.info("Roadmap backup update saved to ${file.absolutePath}")
        }
    }
}