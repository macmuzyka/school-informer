package com.schoolinformer.service.utils

import java.io.File
import java.io.FileNotFoundException

class FileUtils {
    companion object {
        fun fileExists(filePath: String): File? {
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