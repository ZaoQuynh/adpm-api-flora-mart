package com.example.admp_api_flora_mart.dto

import com.example.admp_api_flora_mart.entity.*
import java.time.LocalDateTime

data class OrderDTO (
    var id: Long? = null,
    var customer: UserDTO?= null,
    var orderItems: MutableList<OrderItemDTO> = mutableListOf(),
    var status: EOrderStatus?= null,
    var createDate: LocalDateTime?= null,
    var vouchers: MutableList<VoucherDTO> = mutableListOf(),
    var payment: PaymentDTO?= null,
    var address: String?= null
)