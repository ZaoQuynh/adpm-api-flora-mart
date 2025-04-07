package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.dto.ProductDTO

interface ProductService {
    fun add(product: ProductDTO): ProductDTO
    fun getProducts(): List<ProductDTO>
    fun findTop10SimilarProducts(id: Long): List<ProductDTO>
    fun getProductsByIds(ids: List<Long>): List<ProductDTO>
}