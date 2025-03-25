package com.example.admp_api_flora_mart.controller.cart.request

import com.example.admp_api_flora_mart.dto.CartDTO
import com.example.admp_api_flora_mart.entity.EPaymentType

data class CheckoutRequest(
    val cartDTO: CartDTO,
    val address: String,
    val type: EPaymentType
)