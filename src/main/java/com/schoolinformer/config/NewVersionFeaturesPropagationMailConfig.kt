package com.schoolinformer.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
open class NewVersionFeaturesPropagationMailConfig(
    @Value("\${spring.email.password}")
    val emailPassword: String,
    @Value("\${support-mail}")
    val supportMail: String
) {
    private val log = LoggerFactory.getLogger(NewVersionFeaturesPropagationMailConfig::class.java)
    @Bean
    open fun javaMailSender(): JavaMailSender {
        val pw = String(Base64.getDecoder().decode(emailPassword))
        log.info("Resolved password: $pw")
        val mailSender = JavaMailSenderImpl();
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        mailSender.username = "baseschoolinformer@gmail.com"
        mailSender.password = pw

        val properties = mailSender.javaMailProperties
        properties["mail.transport.protocol"] = "smtp"
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"
        properties["mail.smtp.starttls.required"] = "true"
        //TODO: debug only i devel mode
        properties["mail.debug"] = "true"

        return mailSender
    }
}