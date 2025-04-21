package com.example.admp_api_flora_mart.controller.order.response

data class CashflowStats(
    val year: Int,
    var pendingAmount: Double,
    var shippingAmount: Double,
    var deliveredAmount: Double
)