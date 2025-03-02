package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.AttributeDTO
import com.example.admp_api_flora_mart.entity.Attribute
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class AttributeMapper(private val modelMapper: ModelMapper): Mapper<AttributeDTO, Attribute> {
    override fun toDto(entity: Attribute): AttributeDTO {
        return modelMapper.map(entity, AttributeDTO::class.java)
    }

    override fun toEntity(dto: AttributeDTO): Attribute {
        return modelMapper.map(dto, Attribute::class.java)
    }
}