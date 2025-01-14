package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.reponsitory.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

typealias ApplicationUser = com.example.admp_api_flora_mart.entity.User

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)
            .orElseThrow { AuthenticationException("User not found for email: $username.") }
            .mapToUserDetails()

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role?.name)
            .build()
}