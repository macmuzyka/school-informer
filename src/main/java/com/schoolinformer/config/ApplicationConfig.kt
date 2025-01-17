package com.schoolinformer.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.config")
data class ApplicationConfig(val roadmapBackupDirectory: String, val roadmapBackupFileName: String)