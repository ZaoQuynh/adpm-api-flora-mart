package com.example.admp_api_flora_mart.dto

data class DescriptionGroupDTO(
    var id: Long?= null,
    var name: String?= null,
    var icon: String?= null,
    var descriptions: MutableList<DescriptionDTO> = mutableListOf()
)
