package com.example.admp_api_flora_mart.dto

data class OrderItemDTO(
    var id: Long? = null,
    var product: ProductDTO?= null,
    var discounted: Double?= 0.0,
    var qty: Int?= 0,
    var currentPrice: Double?= 0.0,
    var isReviewed: Boolean,
)
