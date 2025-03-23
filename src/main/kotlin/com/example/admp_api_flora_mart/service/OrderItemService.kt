package com.example.admp_api_flora_mart.service
import com.example.admp_api_flora_mart.controller.orderItem.request.AddToCartRequest
import com.example.admp_api_flora_mart.dto.OrderItemDTO

interface OrderItemService {
    fun addToCart(request: AddToCartRequest): OrderItemDTO
    fun getUserCart(userId: Long): List<OrderItemDTO>
}