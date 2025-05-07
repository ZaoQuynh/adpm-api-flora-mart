package com.example.admp_api_flora_mart.controller.review

import com.example.admp_api_flora_mart.dto.ReviewDTO
import com.example.admp_api_flora_mart.service.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/review")
class ReviewController(private val reviewService: ReviewService) {

    @GetMapping("/{productId}")
    fun getReviewsByProductId(@PathVariable productId: Long): ResponseEntity<Any> {
        return try {
            val reviews = reviewService.getReviewsByProductId(productId);
            ResponseEntity.ok(reviews)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PostMapping("/{orderItemId}")
    fun addReview(@PathVariable orderItemId: Long, @RequestBody reviewDTO: ReviewDTO): ResponseEntity<Any> {
        return try {
            val addedReview = reviewService.create(orderItemId, reviewDTO);
            ResponseEntity.ok(addedReview)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }

    }

    @PatchMapping("/feed-back")
    fun addReview(@RequestBody reviewDTO: ReviewDTO): ResponseEntity<Any> {
        return try {
            val updatedReview = reviewService.update(reviewDTO);
            ResponseEntity.ok(updatedReview)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }

    }
}