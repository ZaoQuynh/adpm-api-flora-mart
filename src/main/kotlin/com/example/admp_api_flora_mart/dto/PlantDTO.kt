package com.example.admp_api_flora_mart.dto


data class PlantDTO(
    var id: Long?= null,
    var name: String?= null,
    var descriptions: MutableList<DescriptionDTO> = mutableListOf(),
    var attributes: MutableList<AttributeDTO> = mutableListOf(),
    var img: String?= null,
)
