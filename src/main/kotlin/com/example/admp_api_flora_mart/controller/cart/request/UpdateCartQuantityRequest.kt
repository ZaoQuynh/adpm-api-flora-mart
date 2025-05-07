package com.example.admp_api_flora_mart.controller.cart.request

data class UpdateCartQuantityRequest(
    val cartId: Long,
    val productId: Long,
    val quantity: Int
)