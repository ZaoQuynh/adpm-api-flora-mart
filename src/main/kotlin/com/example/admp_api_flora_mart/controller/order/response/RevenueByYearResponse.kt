package com.example.admp_api_flora_mart.controller.order.response

data class RevenueByYearResponse(
    val revenueDataByYear: Map<Int, List<Double>>
)