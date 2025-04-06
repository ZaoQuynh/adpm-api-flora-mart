package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "review")
data class Review (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= null,
    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: User?= null,
    var rate: Int?= 0,
    var comment: String?= null,
    var feedback: String?= null,
    var date: LocalDateTime?=null,

    @OneToOne
    @JoinColumn(name = "order_item_id", unique = true)
    var orderItem: OrderItem? = null
)