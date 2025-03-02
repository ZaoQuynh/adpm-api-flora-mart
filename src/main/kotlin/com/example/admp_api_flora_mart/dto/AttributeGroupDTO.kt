package com.example.admp_api_flora_mart.dto

data class AttributeGroupDTO (
    var id: Long? = null,
    var name: String? = null,
    var icon: String? = null,
    var attributes: MutableList<AttributeDTO> = mutableListOf()
)