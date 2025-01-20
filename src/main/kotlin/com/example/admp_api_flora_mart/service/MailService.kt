package com.example.admp_api_flora_mart.service

interface MailService {
    fun sendEmail(to: String, subject: String, body: String)
}