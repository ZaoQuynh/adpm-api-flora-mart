package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.controller.user.request.UpdateUserInfoRequest
import com.example.admp_api_flora_mart.dto.UserDTO
import com.example.admp_api_flora_mart.mapper.UserMapper
import com.example.admp_api_flora_mart.reponsitory.UserRepository
import com.example.admp_api_flora_mart.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
): UserService {
    override fun updateUserInfo(request: UpdateUserInfoRequest): UserDTO {
        val user = request.id?.let {
            userRepository.findById(it)
                .orElseThrow { Exception("User Not Found") }
        } ?: throw Exception("User ID must not be null")

        user.fullName = request.fullName
        user.username = request.username
        user.phoneNumber = request.phoneNumber
        user.avatar = request.avatar

        val updatedUser = userRepository.save(user)
        return userMapper.toDto(updatedUser)
    }
}