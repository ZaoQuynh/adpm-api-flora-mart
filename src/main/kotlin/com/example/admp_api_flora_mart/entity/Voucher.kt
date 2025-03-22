package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "voucher")
data class Voucher(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var code: String?= null,
    var discount: Double?= 0.0,
    var createDate: LocalDateTime?= null,
    var startDate: LocalDateTime?= null,
    var endDate: LocalDateTime?= null,
    @ManyToOne
    @JoinColumn(name = "voucher_type_id", nullable = false)
    var type: VoucherType?= null,
    var description: String?= null,
    var minOrderAmount: Double?= null,
    var maxDiscount: Double?=null
)
