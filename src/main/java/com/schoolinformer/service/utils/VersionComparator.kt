package com.schoolinformer.service.utils

class VersionComparator {
    companion object {
        fun compareVersion(currentVersion: String, incomingVersion: String): Int {
            val currentVersionParts = currentVersion
                .split(".")
                .map { it.toIntOrNull() ?: 0 }
            val incomingVersionParts = incomingVersion
                .split(".")
                .map { it.toIntOrNull() ?: 0 }

            val maxVersionDepth = maxOf(currentVersionParts.size, incomingVersionParts.size)

            return (0 until maxVersionDepth).asSequence()
                .map { index ->
                    val v1 = currentVersionParts.getOrElse(index) { 0 }
                    val v2 = incomingVersionParts.getOrElse(index) { 0 }
                    v2.compareTo(v1)
                }.firstOrNull { it != 0 } ?: 0
        }
    }
}