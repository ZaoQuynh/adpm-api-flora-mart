package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.dto.AttributeGroupDTO

interface AttributeGroupService {
    fun add(attributeGroup: AttributeGroupDTO): AttributeGroupDTO
    fun getAttributeGroups(): List<AttributeGroupDTO>
}