package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.AttributeDTO
import com.example.admp_api_flora_mart.dto.AttributeGroupDTO
import com.example.admp_api_flora_mart.entity.Attribute
import com.example.admp_api_flora_mart.mapper.AttributeGroupMapper
import com.example.admp_api_flora_mart.mapper.AttributeMapper
import com.example.admp_api_flora_mart.reponsitory.AttributeGroupRepository
import com.example.admp_api_flora_mart.reponsitory.AttributeRepository
import com.example.admp_api_flora_mart.service.AttributeService
import org.springframework.stereotype.Service
@Service
class AttributeServiceImpl(
    private val attributeRepository: AttributeRepository,
    private val attributeMapper: AttributeMapper,
    private val attributeGroupRepository: AttributeGroupRepository,
    private val attributeGroupMapper: AttributeGroupMapper
): AttributeService {
    override fun add(attributeDTO: AttributeDTO?, attributeGroupDTO: AttributeGroupDTO?): AttributeDTO {
        if(!attributeGroupRepository.existsByName(attributeGroupDTO?.name)){
            throw Exception("Attribute Group does not exist:  ${attributeDTO?.name}")
        }

        if(attributeRepository.existsByName(attributeDTO?.name)){
            throw Exception("Attribute already exists:  ${attributeDTO?.name}")
        }

        val attribute = Attribute(
            name = attributeDTO?.name,
            icon = attributeDTO?.icon,
            attributeGroup = attributeGroupDTO?.let { attributeGroupMapper.toEntity(it) }
        )

        val savedAttribute = attributeRepository.save(attribute)
        return attributeMapper.toDto(savedAttribute)
    }

    override fun getAttributes(): List<AttributeDTO> {
        val attributes = attributeRepository.findAll();
        return attributes.map { attributeMapper.toDto(it) }
    }


}