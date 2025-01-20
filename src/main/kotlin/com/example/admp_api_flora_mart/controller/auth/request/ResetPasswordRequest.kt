package com.example.admp_api_flora_mart.controller.auth.request

data class ResetPasswordRequest(
    var email: String,
    var newPassword: String
)
