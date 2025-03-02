package com.example.admp_api_flora_mart.controller.user

import com.example.admp_api_flora_mart.controller.user.request.UpdateUserInfoRequest
import com.example.admp_api_flora_mart.dto.UserDTO
import com.example.admp_api_flora_mart.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val userService: UserService) {
    @PatchMapping("/update")
    fun updateUserInfo(@RequestBody request: UpdateUserInfoRequest): ResponseEntity<Any> {
        return try {
            val updatedUser: UserDTO = userService.updateUserInfo(request)
            ResponseEntity.ok(updatedUser)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }
}