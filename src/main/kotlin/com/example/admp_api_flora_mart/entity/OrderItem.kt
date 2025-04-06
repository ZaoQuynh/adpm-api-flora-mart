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

    @OneToOne(mappedBy = "orderItem", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var review: Review? = null,

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = true)
    var order: Order? = null,

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = true)
    var cart: Cart? = null,

    @OneToOne(mappedBy = "orderItem", cascade = [CascadeType.ALL])
    var review: Review? = null
)
