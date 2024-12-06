package com.schoolinformer.model.dto

import java.time.LocalDateTime

data class ContentDTO(val content: String = "", val createdAt: LocalDateTime = LocalDateTime.now())