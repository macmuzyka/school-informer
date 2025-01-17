package com.schoolinformer.service.utils

import org.slf4j.LoggerFactory
import java.io.File

class FileUtils {
    companion object {
        private val log = LoggerFactory.getLogger(FileUtils::class.java)
        fun fileExists(filePath: String): File? {
            log.info("Exist check for file with path: $filePath")
            return File(filePath).takeIf { it.exists() }
        }

        fun getDirectory(directoryPath: String): File {
            return File(directoryPath).takeIf { it.isDirectory } ?: createBackupDirectory(directoryPath)
        }

        private fun createBackupDirectory(directoryPath: String): File {
            val directory = File(directoryPath)
            directory.mkdirs()
            directory.isDirectory
            return directory
        }
    }
}