package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payment")
data class Payment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var paymentDate: LocalDateTime?= LocalDateTime.now(),
    var type: EPaymentType?= null,
    var status: EPaymentStatus?= null
)

enum class EPaymentStatus{
    PENDING, SUCCESS, FAILED
}

enum class EPaymentType{
    COD, MOMO
}