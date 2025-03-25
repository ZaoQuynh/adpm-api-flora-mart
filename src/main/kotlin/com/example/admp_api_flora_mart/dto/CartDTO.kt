package com.example.admp_api_flora_mart.dto

import com.example.admp_api_flora_mart.entity.OrderItem

data class CartDTO(
    var id: Long? = null,
    var customer: UserDTO?= null,
    var orderItems: MutableList<OrderItemDTO> = mutableListOf()
)