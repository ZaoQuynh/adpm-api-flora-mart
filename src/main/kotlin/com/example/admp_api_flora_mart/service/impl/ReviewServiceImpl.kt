package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.ReviewDTO
import com.example.admp_api_flora_mart.entity.Review
import com.example.admp_api_flora_mart.mapper.ReviewMapper
import com.example.admp_api_flora_mart.reponsitory.OrderItemRepository
import com.example.admp_api_flora_mart.reponsitory.ReviewRepository
import com.example.admp_api_flora_mart.service.ReviewService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.naming.AuthenticationException

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val reviewMapper: ReviewMapper,
    private val orderItemRepository: OrderItemRepository
): ReviewService {

    override fun getReviewsByProductId(productId: Long): List<ReviewDTO> {
        return try {
            orderItemRepository.findByProductId(productId)
                .mapNotNull { it.review?.let { it1 -> reviewMapper.toDto(it1) } }
        } catch (ex: Exception) {
            emptyList()
        }
    }

    override fun create(orderItemId: Long, reviewDTO: ReviewDTO): ReviewDTO {
        try {
            val orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow { Exception("Order Item not found by Id: $orderItemId.") }

            if (orderItem.review != null) {
                throw IllegalStateException("OrderItem already has a review.")
            }

            var review = reviewMapper.toEntity(reviewDTO)
            review.orderItem = orderItem
            review.date = LocalDateTime.now()

            val createdReview = reviewRepository.save(review)
            return reviewMapper.toDto(createdReview)
        } catch (ex: Exception) {
            throw RuntimeException("Failed to create review: ${ex.message}", ex)
        }
    }

    override fun update(reviewDTO: ReviewDTO): ReviewDTO {
        try {
            var review = reviewMapper.toEntity(reviewDTO)
            val createdReview = reviewRepository.save(review)
            return reviewMapper.toDto(createdReview)
        } catch (ex: Exception) {
            throw RuntimeException("Failed to update review: ${ex.message}", ex)
        }
    }
}