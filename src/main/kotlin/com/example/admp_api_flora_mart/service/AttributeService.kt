package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.dto.AttributeDTO
import com.example.admp_api_flora_mart.dto.AttributeGroupDTO

interface AttributeService {
    fun add(attributeDTO: AttributeDTO?, attributeGroupDTO: AttributeGroupDTO?): AttributeDTO
    fun getAttributes(): List<AttributeDTO>
}