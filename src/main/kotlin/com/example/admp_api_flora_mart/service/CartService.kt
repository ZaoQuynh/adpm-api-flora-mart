package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.controller.cart.request.CheckoutRequest
import com.example.admp_api_flora_mart.controller.cart.request.UpdateCartQuantityRequest
import com.example.admp_api_flora_mart.dto.CartDTO

interface CartService {
    fun add(cartDTO: CartDTO): CartDTO
    fun getCartByUser(email: String): CartDTO
    fun checkOut(request: CheckoutRequest): CartDTO
    fun updateQuantity(request: UpdateCartQuantityRequest): CartDTO
    fun getCartIdByUserId(userId: Long): Long
}