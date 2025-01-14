package com.example.admp_api_flora_mart.controller.auth.request

data class RegisterRequest(
    var fullName: String?= null,
    var email: String?= null,
    var username: String?= null,
    var password: String?= null
)
