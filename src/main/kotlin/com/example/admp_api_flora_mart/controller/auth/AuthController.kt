package com.example.admp_api_flora_mart.controller.auth

import com.example.admp_api_flora_mart.controller.auth.request.AuthRequest
import com.example.admp_api_flora_mart.controller.auth.request.RegisterRequest
import com.example.admp_api_flora_mart.controller.auth.response.AuthResponse
import com.example.admp_api_flora_mart.dto.UserDTO
import com.example.admp_api_flora_mart.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
){

    @GetMapping
    fun helloWorld(): String {
        return "Hello";
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<AuthResponse> {
        val authResponse = authService.authenticate(authRequest.email!!, authRequest.password!!)
        return ResponseEntity.ok(authResponse)
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<UserDTO> {
        val userDTO = authService.register(registerRequest)
        return ResponseEntity.ok(userDTO)
    }
}