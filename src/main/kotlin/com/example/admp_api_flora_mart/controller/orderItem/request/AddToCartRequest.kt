package com.example.admp_api_flora_mart.controller.orderItem.request

data class AddToCartRequest(
    var customerId: Long,
    var productId: Long,
    var quantity: Int
)