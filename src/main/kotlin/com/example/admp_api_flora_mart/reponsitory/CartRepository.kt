package com.example.admp_api_flora_mart.reponsitory

import com.example.admp_api_flora_mart.entity.Cart
import com.example.admp_api_flora_mart.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CartRepository: JpaRepository<Cart, Long> {
    fun findByCustomerId(customerId: Long): Optional<Cart>
    //fun findByUser(user: User): Optional<Cart>
}