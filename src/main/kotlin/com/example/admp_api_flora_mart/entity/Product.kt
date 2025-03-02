package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*

@Entity
@Table(name = "product")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= null,

    @ManyToOne
    @JoinColumn(name = "plant_id")
    var plant: Plant?= null,
    var price: Double?= 0.0,
    var discount: Double?= 0.0,
    var stockQty: Int?= 0,
    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var reviews: MutableList<Review> = mutableListOf(),

    var isDeleted: Boolean? = false
)
