package com.example.admp_api_flora_mart.controller.orderItem.request

import com.example.admp_api_flora_mart.dto.CartDTO
import com.example.admp_api_flora_mart.dto.ProductDTO

data class AddToCartRequest(
    val productDTO: ProductDTO,
    val cartDTO: CartDTO
)