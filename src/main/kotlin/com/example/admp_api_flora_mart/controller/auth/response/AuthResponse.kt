package com.example.admp_api_flora_mart.controller.auth.response

import com.example.admp_api_flora_mart.entity.ERole
import com.example.admp_api_flora_mart.entity.EUserStatus

data class AuthResponse (
    var accessToken: String?= null,
    var refreshToken: String?= null,
    var role: ERole?= null,
    var status: EUserStatus?= null
)