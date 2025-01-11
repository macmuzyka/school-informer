package com.schoolinformer.service.utils

import com.schoolinformer.model.dto.FeatureDisplayDTO
import com.schoolinformer.model.enums.DevelopmentStage
import org.springframework.kafka.support.SendResult
import java.util.concurrent.CompletableFuture

class KafkaUtils {
    companion object {
        fun <T> CompletableFuture<SendResult<String, T>>.getMessageSentViaKafkaCompletableFuture(): T {
            return this.get().producerRecord.value()
        }

        fun Map<DevelopmentStage, List<FeatureDisplayDTO>>.plain(): Map<String, List<String>> {
            return this.map { (developmentStage, featureList) ->
                val key = developmentStage.name
                val value = featureList.map { it.description }
                key to value
            }.toMap()
        }
    }
}