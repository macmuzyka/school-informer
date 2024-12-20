package com.schoolinformer.service.utils

class MailUtils {
    companion object {
        fun prepareMailMessage(features: List<String>): String {
                return "New features introduced in latest version: \n" +
                        prepareNewFeaturesAsIndexedPlainText(features) + "\n\n" + 
                        "Feel free to test new features and give us feedback!\n" +
                        "[This message is auto generated, do not reply]"
            }

            private fun prepareNewFeaturesAsIndexedPlainText(features: List<String>): String {
                return features
                    .mapIndexed { index, feature -> "${index + 1}. $feature" }
                    .joinToString("\n")
            }

        fun getMailSubject(): String {
            return "New school application version with new features released!"
        }
    }
}