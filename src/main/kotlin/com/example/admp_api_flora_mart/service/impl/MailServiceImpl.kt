package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.service.MailService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailServiceImpl(private val mailSender: JavaMailSender): MailService {

    private val logger: Logger = LoggerFactory.getLogger(MailServiceImpl::class.java)

    override fun sendEmail(to: String, subject: String, body: String) {
        logger.info("Preparing to send email to: $to, subject: $subject")

        try {
            val message = SimpleMailMessage()
            message.setTo(to)
            message.subject = subject
            message.text = body
            mailSender.send(message)

            logger.info("Email successfully sent to: $to")
        } catch (ex: Exception) {
            logger.error("Failed to send email to: $to. Error: ${ex.message}", ex)
        }
    }
}