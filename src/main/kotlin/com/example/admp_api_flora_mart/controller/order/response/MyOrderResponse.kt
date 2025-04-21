package com.example.admp_api_flora_mart.controller.order.response

import com.example.admp_api_flora_mart.dto.OrderDTO

data class MyOrderResponse(
    val orders: List<OrderDTO>,
    val statsByYear: List<CashflowStats>
)