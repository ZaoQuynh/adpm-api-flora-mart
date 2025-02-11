package com.example.admp_api_flora_mart.dto

import com.example.admp_api_flora_mart.entity.ERole
import com.example.admp_api_flora_mart.entity.EUserStatus
import com.example.admp_api_flora_mart.entity.EUserTier

data class UserDTO(
    var id: Long?= null,
    var fullName: String?= null,
    var email: String?= null,
    var phoneNumber: String?= null,
    var username: String?= null,
    var tier: EUserTier?= null,
    var points: Int?= null,
    var status: EUserStatus?= null,
    var role: ERole?= null,
    var tokenRefresh: String?= null,
    var avatar: String?= null,
)
