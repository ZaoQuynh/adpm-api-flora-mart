package com.example.admp_api_flora_mart.entity

import jakarta.persistence.Entity

import jakarta.persistence.*

@Entity
@Table(name = "cart")
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    var customer: User,

    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL], orphanRemoval = true)
    var orderItems: MutableList<OrderItem> = mutableListOf()
)
