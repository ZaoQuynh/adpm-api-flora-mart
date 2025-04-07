package com.example.admp_api_flora_mart.reponsitory

import com.example.admp_api_flora_mart.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<Review, Long> {
}