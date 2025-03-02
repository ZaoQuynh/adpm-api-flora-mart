package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.AttributeGroupDTO
import com.example.admp_api_flora_mart.entity.AttributeGroup
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class AttributeGroupMapper(private val modelMapper: ModelMapper): Mapper<AttributeGroupDTO, AttributeGroup>{
    override fun toDto(entity: AttributeGroup): AttributeGroupDTO {
        return modelMapper.map(entity, AttributeGroupDTO::class.java)
    }

    override fun toEntity(dto: AttributeGroupDTO): AttributeGroup {
        return modelMapper.map(dto, AttributeGroup::class.java)
    }

}
