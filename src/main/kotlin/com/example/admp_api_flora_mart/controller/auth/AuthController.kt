    package com.example.admp_api_flora_mart.controller.auth

    import com.example.admp_api_flora_mart.controller.auth.request.AuthRequest
    import com.example.admp_api_flora_mart.controller.auth.request.RegisterRequest
    import com.example.admp_api_flora_mart.controller.auth.request.ResetPasswordRequest
    import com.example.admp_api_flora_mart.service.AuthService
    import org.springframework.http.ResponseEntity
    import org.springframework.web.bind.annotation.*

    @RestController
    @RequestMapping("/api/v1/auth")
    class AuthController(
        private val authService: AuthService
    ){
        @PostMapping("/login")
        fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<Any> {
            return try {
                val authResponse = authService.authenticate(authRequest.email!!, authRequest.password!!)
                ResponseEntity.ok(authResponse)
            } catch (ex: Exception) {
                ResponseEntity.badRequest().body(mapOf("error" to ex.message))
            }
        }

        @PostMapping("/register")
        fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<Any> {
            return try {
                val userDTO = authService.register(registerRequest)
                ResponseEntity.ok(userDTO)
            } catch (ex: Exception) {
                ResponseEntity.badRequest().body(mapOf("error" to ex.message))
            }
        }

        @PutMapping("/verify")
        fun verify(@RequestBody body: Map<String, String>): ResponseEntity<Any> {
            return try {
                val email = body["email"] ?: throw IllegalArgumentException("Email is required")
                val verifiedUser = authService.verifyByEmail(email)
                verifiedUser?.let {
                    ResponseEntity.ok(it)
                } ?: ResponseEntity.notFound().build()
            } catch (ex: Exception) {
                ResponseEntity.badRequest().body(mapOf("error" to ex.message))
            }
        }

        @PutMapping("/reset-password")
        fun resetPassword(@RequestBody resetPasswordRequest: ResetPasswordRequest): ResponseEntity<Any> {
            return try {
                val updatedUser = authService.resetPassword(resetPasswordRequest.email, resetPasswordRequest.newPassword)
                updatedUser?.let {
                    ResponseEntity.ok(it)
                } ?: ResponseEntity.notFound().build()
            } catch (ex: Exception) {
                ResponseEntity.badRequest().body(mapOf("error" to ex.message))
            }
        }

        @PostMapping("/check-email")
        fun checkEmailExist(@RequestBody body: Map<String, String>): ResponseEntity<Any> {
            return try {
                val email = body["email"] ?: throw IllegalArgumentException("Email is required")
                val exists  = authService.isEmailExist(email)
                ResponseEntity.ok(exists)
            } catch (ex: Exception) {
                ResponseEntity.badRequest().body(mapOf("error" to ex.message))
            }
        }

        @PostMapping("/check-username")
        fun checkUsernameExist(@RequestBody body: Map<String, String>): ResponseEntity<Any> {
            return try {
                val username = body["username"] ?: throw IllegalArgumentException("Username is required")
                val exists  = authService.isUsernameExist(username)
                ResponseEntity.ok(exists)
            } catch (ex: Exception) {
                ResponseEntity.badRequest().body(mapOf("error" to ex.message))
            }
        }
    }