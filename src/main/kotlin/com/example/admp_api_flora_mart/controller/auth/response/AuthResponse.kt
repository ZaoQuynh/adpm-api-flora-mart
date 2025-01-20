package com.example.admp_api_flora_mart.controller.auth.response

import com.example.admp_api_flora_mart.dto.UserDTO

data class AuthResponse (
    var accessToken: String?= null,
    var user: UserDTO?= null
)