package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.controller.orderItem.request.AddToCartRequest
import com.example.admp_api_flora_mart.dto.OrderDTO

interface OrderService {
    fun addToCart(request: AddToCartRequest): OrderDTO
}