package com.example.admp_api_flora_mart.service
import com.example.admp_api_flora_mart.controller.orderItem.request.AddToCartRequest
import com.example.admp_api_flora_mart.dto.CartDTO
import com.example.admp_api_flora_mart.dto.OrderItemDTO
import com.example.admp_api_flora_mart.entity.OrderItem

interface OrderItemService {
    fun addToCart(request: AddToCartRequest) : OrderItemDTO
    fun updateItemQuantity(itemId: Long, qty: Int): OrderItemDTO
    fun removeItemFromCart(itemId: Long)
    fun soldQtyByProductId(id: Long?): Int
}