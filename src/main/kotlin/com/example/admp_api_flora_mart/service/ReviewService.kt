package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.dto.ReviewDTO

interface ReviewService {
    fun getReviewsByProductId(productId: Long): List<ReviewDTO>
    fun create(orderItemId: Long, reviewDTO: ReviewDTO): ReviewDTO
    fun update(reviewDTO: ReviewDTO): ReviewDTO
}