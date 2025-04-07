package com.example.admp_api_flora_mart.dto

data class ProductDTO(
    var id: Long?= null,
    var plant: PlantDTO?= null,
    var price: Double?= 0.0,
    var discount: Double?= 0.0,
    var stockQty: Int?= 0,
    var isDeleted: Boolean? = false,
    var soldQty: Int?= 0
)
