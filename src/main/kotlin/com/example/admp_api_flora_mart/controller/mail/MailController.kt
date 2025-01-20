package com.example.admp_api_flora_mart.controller.mail

import com.example.admp_api_flora_mart.controller.mail.request.SendMailRequest
import com.example.admp_api_flora_mart.service.MailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/mail")
class MailController(private val mailService: MailService) {

    @PostMapping("/send")
    fun sendEmail(@RequestBody mail: SendMailRequest): ResponseEntity<Any>{
        return try {
            mailService.sendEmail(mail.email, mail.subject, mail.body)
            ResponseEntity.ok("Email sent successfully!")
        } catch (ex: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}