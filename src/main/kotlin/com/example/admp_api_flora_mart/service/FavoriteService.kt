package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.dto.ProductDTO
interface FavoriteService {
    fun getFavoriteProducts(userId: Long): List<ProductDTO>
    fun addFavoriteProduct(userId: Long, productId: Long)
    fun removeFavoriteProduct(userId: Long, productId: Long)
    fun checkFavorite(userId: Long, productId: Long): Boolean
}