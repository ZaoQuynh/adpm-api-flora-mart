package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.dto.UserDTO
import org.springframework.security.core.userdetails.UserDetails

interface RefreshTokenService {
    fun findUserDetailsByToken(token: String): UserDetails?
    fun save(token: String, userDetails: UserDetails): UserDTO
}