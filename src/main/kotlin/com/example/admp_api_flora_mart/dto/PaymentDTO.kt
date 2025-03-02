package com.example.admp_api_flora_mart.dto

import com.example.admp_api_flora_mart.entity.EPaymentStatus
import com.example.admp_api_flora_mart.entity.EPaymentType
import java.time.LocalDateTime

data class PaymentDTO (
    var id: Long? = null,
    var paymentDate: LocalDateTime?= LocalDateTime.now(),
    var type: EPaymentType?= null,
    var status: EPaymentStatus?= null
)