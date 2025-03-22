package com.example.admp_api_flora_mart.dto

import java.time.LocalDateTime

data class VoucherDTO (
    var id: Long? = null,
    var code: String?= null,
    var discount: Double?= 0.0,
    var createDate: LocalDateTime?= null,
    var startDate: LocalDateTime?= null,
    var endDate: LocalDateTime?= null,
    var type: VoucherTypeDTO?= null,
    var description: String?= null,
    var minOrderAmount: Double?= null,
    var maxDiscount: Double?= null
)