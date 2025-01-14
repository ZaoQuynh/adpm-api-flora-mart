package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.UserDTO
import com.example.admp_api_flora_mart.mapper.UserMapper
import com.example.admp_api_flora_mart.reponsitory.UserRepository
import com.example.admp_api_flora_mart.service.RefreshTokenService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class RefreshTokenServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
): RefreshTokenService {
    override fun findUserDetailsByToken(token: String): UserDetails {
        return userRepository.findByTokenRefresh(token)
            .orElseThrow { AuthenticationException("User not found for provided token.") }
            .mapToUserDetails()
    }

    override fun save(token: String, userDetails: UserDetails): UserDTO {
        val user = userRepository.findByEmail(userDetails.username)
            .orElseThrow { AuthenticationException("User not found for email: ${userDetails.username}.") }

        user.tokenRefresh = token
        userRepository.save(user)
        return userMapper.toDto(user)
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role?.name)
            .build()
}