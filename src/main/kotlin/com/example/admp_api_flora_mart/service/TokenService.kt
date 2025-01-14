package com.example.admp_api_flora_mart.service

import org.springframework.security.core.userdetails.UserDetails
import java.util.*

interface TokenService {
    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String
    fun extractEmail(jwtToken: String): String?
    fun isExpired(token: String): Boolean
    fun isValid(jwtToken: String, foundUser: UserDetails): Boolean
    fun validateToken(token: String): Boolean
    fun invalidateToken(token: String)
}