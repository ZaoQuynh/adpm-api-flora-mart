package com.example.admp_api_flora_mart.service
import com.example.admp_api_flora_mart.dto.CartDTO
import com.example.admp_api_flora_mart.dto.OrderItemDTO
import com.example.admp_api_flora_mart.entity.OrderItem

interface OrderItemService {
    fun addToCart(orderItem: OrderItemDTO, cartDTO: CartDTO) : OrderItemDTO
    //fun addItemToCart(customerId: Long, newItem: OrderItem): OrderItemDTO
    fun updateItemQuantity(itemId: Long, qty: Int): OrderItemDTO
    fun removeItemFromCart(itemId: Long)
}