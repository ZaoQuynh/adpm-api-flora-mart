package com.example.admp_api_flora_mart.controller.mail.request

data class SendMailRequest (
    var email: String,
    var subject: String,
    var body: String
)