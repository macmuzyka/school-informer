package com.schoolinformer.deserializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.schoolinformer.model.dto.FeedbackDTO
import org.apache.kafka.common.serialization.Deserializer

class FeedbackDTODeserializer : Deserializer<FeedbackDTO> {
    private val mapper = ObjectMapper()
    override fun deserialize(topic: String?, data: ByteArray?): FeedbackDTO {
        return try {
            mapper.readValue(data, FeedbackDTO::class.java)
        } catch (e: Exception) {
            throw RuntimeException("Failed to deserialize FeedbackDTO", e)
        }
    }
}