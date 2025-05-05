package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.controller.order.response.CashflowStats
import com.example.admp_api_flora_mart.controller.order.response.MyOrderResponse
import com.example.admp_api_flora_mart.controller.order.response.RevenueByYearResponse
import com.example.admp_api_flora_mart.dto.OrderDTO

interface OrderService {
    fun getOrders(): List<OrderDTO>
    fun add(orderDTO: OrderDTO): OrderDTO
    fun getOrdersByUser(email: String): List<OrderDTO>
    fun updateOrderStatus(orderId: Long): OrderDTO
    fun cancel(orderId: Long): OrderDTO
    fun receive(orderId: Long): OrderDTO
    fun getById(orderId: Long): OrderDTO
    fun calculateCashflowByUser(email: String): MyOrderResponse
    fun calculateRevenueByYear(): RevenueByYearResponse
}