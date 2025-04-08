package com.example.admp_api_flora_mart.reponsitory

import com.example.admp_api_flora_mart.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository : JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.customer.id = :userId AND (oi.order.payment IS NULL OR oi.order.payment.type IS NULL)")
    fun findUnpaidOrderItemsByUser(userId: Long): List<OrderItem>
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.customer.id = :userId AND oi.product.id = :productId AND (oi.order.payment IS NULL OR oi.order.payment.type IS NULL)")
    fun findByOrderCustomerAndProduct(userId: Long, productId: Long): OrderItem?
    fun findByProductId(productId: Long): List<OrderItem>
}