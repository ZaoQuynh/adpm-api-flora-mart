package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*

@Entity
@Table(name = "order_item")
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product?= null,
    var discounted: Double?= 0.0,
    var qty: Int?= 0,
    var currentPrice: Double?= 0.0,

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    var order: Order? = null,

    @OneToOne(mappedBy = "orderItem", cascade = [CascadeType.ALL])
    var review: Review? = null
)
