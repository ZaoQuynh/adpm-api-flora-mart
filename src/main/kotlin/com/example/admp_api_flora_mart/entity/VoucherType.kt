package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*

@Entity
@Table(name = "voucher_type")
data class VoucherType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= null,
    var type: String?= null
)