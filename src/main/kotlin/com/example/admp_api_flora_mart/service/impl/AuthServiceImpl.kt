package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.config.JwtProperties
import com.example.admp_api_flora_mart.controller.auth.request.RegisterRequest
import com.example.admp_api_flora_mart.controller.auth.response.AuthResponse
import com.example.admp_api_flora_mart.dto.UserDTO
import com.example.admp_api_flora_mart.entity.ERole
import com.example.admp_api_flora_mart.entity.EUserStatus
import com.example.admp_api_flora_mart.entity.EUserTier
import com.example.admp_api_flora_mart.entity.User
import com.example.admp_api_flora_mart.mapper.UserMapper
import com.example.admp_api_flora_mart.reponsitory.UserRepository
import com.example.admp_api_flora_mart.service.AuthService
import com.example.admp_api_flora_mart.service.RefreshTokenService
import com.example.admp_api_flora_mart.service.TokenService
import com.example.admp_api_flora_mart.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.naming.AuthenticationException

@Service
class AuthServiceImpl(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenService: RefreshTokenService,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
): AuthService {

    override fun authenticate(email: String, password: String): AuthResponse {
        return try {
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    email,
                    password
                )
            )

            val user = userDetailsService.loadUserByUsername(email)

            val accessToken = generateAccessToken(user)
            val refreshToken = generateRefreshToken(user)

            val userDTO = refreshTokenService.save(refreshToken, user)

            AuthResponse(
                accessToken = accessToken,
                refreshToken = refreshToken,
                role = userDTO.role?:ERole.CUSTOMER,
                status = userDTO.status
            )
        } catch (e: Exception) {
            throw AuthenticationException("Authentication failed: ${e.message}")
        }
    }

    override fun register(registerRequest: RegisterRequest): UserDTO {
        TODO("Not yet implemented")
    }

    override fun getUserByToken(jwt: String): UserDTO {
        if (!tokenService.validateToken(jwt)) {
            throw AuthenticationException("Invalid or expired token")
        }

        val email = tokenService.extractEmail(jwt)
            ?: throw AuthenticationException("Email not found in the token")

        val user = userRepository.findByEmail(email)
            .orElseThrow { AuthenticationException("User not found for email: $email") }

        return userMapper.toDto(user)
    }

//    override fun register(registerRequest: RegisterRequest): UserDTO {
//        if (userRepository.existsByEmail(registerRequest.email!!)) {
//            throw AuthenticationException("Email already exists: ${registerRequest.email}")
//        }
//
//        val user = User(
//            fullName = registerRequest.fullName,
//            email = registerRequest.email,
//            username = registerRequest.username,
//            password = passwordEncoder.encode(registerRequest.password),
//            tier = EUserTier.BRONZE,
//            points = 0,
//            status = EUserStatus.PENDING,
//            role = ERole.CUSTOMER
//        )
//
//        val savedUser = userRepository.save(user)
//        return userMapper.toDto(savedUser)
//    }

    private fun generateRefreshToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
    )

    private fun generateAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
    )
}