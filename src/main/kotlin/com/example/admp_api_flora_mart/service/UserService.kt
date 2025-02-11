package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.controller.user.request.UpdateUserInfoRequest
import com.example.admp_api_flora_mart.dto.UserDTO

interface UserService {
    fun updateUserInfo(request: UpdateUserInfoRequest): UserDTO
}