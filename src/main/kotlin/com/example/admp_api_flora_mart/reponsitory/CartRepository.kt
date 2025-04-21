package com.example.admp_api_flora_mart.reponsitory

import com.example.admp_api_flora_mart.entity.Cart
import com.example.admp_api_flora_mart.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface CartRepository: JpaRepository<Cart, Long> {
    fun findByCustomerId(customerId: Long): Optional<Cart>
    @Query("SELECT c.id FROM Cart c WHERE c.customer.id = :customerId")
    fun findCartIdByUserId(@Param("customerId") customerId: Long): Optional<Long>
}