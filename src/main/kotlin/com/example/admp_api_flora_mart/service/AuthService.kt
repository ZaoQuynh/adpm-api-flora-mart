package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.controller.auth.request.RegisterRequest
import com.example.admp_api_flora_mart.controller.auth.response.AuthResponse
import com.example.admp_api_flora_mart.dto.UserDTO

interface AuthService {
    fun authenticate(email: String, password: String): AuthResponse
    fun register(registerRequest: RegisterRequest): UserDTO
    fun getUserByToken(jwt: String): UserDTO
}