package com.example.admp_api_flora_mart.reponsitory

import com.example.admp_api_flora_mart.entity.EOrderStatus
import com.example.admp_api_flora_mart.entity.Order
import com.example.admp_api_flora_mart.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Long>{
    fun findAllByCustomer(customer: User): List<Order>
    @Query("SELECT o FROM Order o WHERE o.customer.id = :userId AND (o.payment IS NULL OR o.payment.type IS NULL)")
    fun findUnpaidOrderByUser(userId: Long): List<Order>
}