package com.example.admp_api_flora_mart.controller.cart

import com.example.admp_api_flora_mart.controller.cart.request.CheckoutRequest
import com.example.admp_api_flora_mart.controller.cart.request.UpdateCartQuantityRequest
import com.example.admp_api_flora_mart.dto.CartDTO
import com.example.admp_api_flora_mart.service.CartService
import com.example.admp_api_flora_mart.service.OrderService
import com.example.admp_api_flora_mart.service.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/cart")
class CartController(
    private val cartService: CartService,
    private val tokenService: TokenService
) {

    @PostMapping
    fun addCart(@RequestBody cartDTO: CartDTO): ResponseEntity<Any> {
        return try {
            val newCart = cartService.add(cartDTO)
            ResponseEntity.ok(newCart)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @GetMapping("/my-cart")
    fun getMyOrders(@RequestHeader("Authorization") token: String): ResponseEntity<Any>{
        return try {
            val jwt = token.removePrefix("Bearer ")
            val email = tokenService.extractEmail(jwt)
            if (email == null) {
                return ResponseEntity.status(401).body(mapOf("error" to "Invalid token"))
            }
            val cart = cartService.getCartByUser(email)
            return ResponseEntity.ok(cart)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
    @PostMapping("/check-out")
    fun checkOut(@RequestBody request: CheckoutRequest): ResponseEntity<Any>{
        return try {
            val checkOut = cartService.checkOut(request)
            ResponseEntity.ok(checkOut)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PutMapping("/update-quantity")
    fun updateQuantity(
        @RequestBody updateRequest: UpdateCartQuantityRequest
    ): ResponseEntity<Any> {
        return try {
            val updatedCart = cartService.updateQuantity(updateRequest)
            ResponseEntity.ok(updatedCart)
        } catch (ex: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}