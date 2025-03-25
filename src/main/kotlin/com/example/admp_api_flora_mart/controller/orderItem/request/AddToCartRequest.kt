package com.example.admp_api_flora_mart.controller.orderItem.request

import com.example.admp_api_flora_mart.dto.CartDTO
import com.example.admp_api_flora_mart.dto.OrderItemDTO

data class AddToCartRequest(
    val orderItemDTO: OrderItemDTO,
    val cartDTO: CartDTO
)