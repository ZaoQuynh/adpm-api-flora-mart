package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.AttributeGroupDTO
import com.example.admp_api_flora_mart.entity.Attribute
import com.example.admp_api_flora_mart.entity.AttributeGroup
import com.example.admp_api_flora_mart.mapper.AttributeGroupMapper
import com.example.admp_api_flora_mart.reponsitory.AttributeGroupRepository
import com.example.admp_api_flora_mart.service.AttributeGroupService
import org.springframework.stereotype.Service

@Service
class AttributeGroupServiceImpl(
    private val attributeGroupRepository: AttributeGroupRepository,
    private val attributeGroupMapper: AttributeGroupMapper
): AttributeGroupService {
    override fun add(attributeGroupDTO: AttributeGroupDTO): AttributeGroupDTO {
        if(attributeGroupRepository.existsByName(attributeGroupDTO.name)){
            throw Exception("Attribute Group already exist:  ${attributeGroupDTO?.name}")
        }

        val attributeGroup = AttributeGroup(
            name = attributeGroupDTO.name,
            icon = attributeGroupDTO.icon
        )

        val savedAttributeGroup = attributeGroupRepository.save(attributeGroup)
        return attributeGroupMapper.toDto(savedAttributeGroup)
    }
}