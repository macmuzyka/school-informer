package com.schoolinformer.service

import com.schoolinformer.model.response.NewVersionFeaturesPropagationResponse
import com.schoolinformer.repository.FeedbackProviderRepository
import com.schoolinformer.service.utils.MailUtils.Companion.getMailSubject
import com.schoolinformer.service.utils.MailUtils.Companion.prepareMailMessage
import org.slf4j.LoggerFactory
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class NewVersionFeaturesPropagationService(
    private val javaMailSender: JavaMailSender,
    private val feedbackProviderRepository: FeedbackProviderRepository,
    private val roadMapService: RoadMapService
) {
    private val log = LoggerFactory.getLogger(NewVersionFeaturesPropagationService::class.java)

    fun propagateNewVersionFeaturesAmongFeedbackProviders(): NewVersionFeaturesPropagationResponse {
        val mailsToUse = feedbackProviderRepository.findAllFeedbackProviderEmails()
        val doneInReleasedVersion = roadMapService.getCurrentTodos()["Done"] ?: emptyList()

        log.info("Mails to send information: $mailsToUse")
        log.info("Done things to be sent: $doneInReleasedVersion")


        try {
            val fullMailMessage = prepareMailMessage(doneInReleasedVersion)
                .also { log.info("Prepared mail message: $it") }
            for (currentMail in mailsToUse) {
                log.info("Sending mail to: $currentMail")
                val mail = SimpleMailMessage()
                mail.setTo(currentMail)
                mail.subject = getMailSubject()
                mail.text = fullMailMessage
                javaMailSender.send(mail)
            }
        } catch (e: Exception) {
            log.error("Error sending mail to feedback providers!")
            log.error(e.message)
        }

        return NewVersionFeaturesPropagationResponse(mailsToUse, doneInReleasedVersion)
    }
}