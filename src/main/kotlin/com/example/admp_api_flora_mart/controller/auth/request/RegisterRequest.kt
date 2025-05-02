package com.example.admp_api_flora_mart.controller.auth.request

import com.example.admp_api_flora_mart.entity.ERole

data class RegisterRequest(
    var fullName: String?= null,
    var email: String?= null,
    var username: String?= null,
    var phoneNumber: String?= null,
    var password: String?= null,
    var role: ERole?= null
)
