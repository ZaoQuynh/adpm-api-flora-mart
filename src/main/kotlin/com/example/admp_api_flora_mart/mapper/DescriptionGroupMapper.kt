package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.DescriptionGroupDTO
import com.example.admp_api_flora_mart.entity.DescriptionGroup
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class DescriptionGroupMapper(private val modelMapper: ModelMapper): Mapper<DescriptionGroupDTO, DescriptionGroup> {
    override fun toDto(entity: DescriptionGroup): DescriptionGroupDTO {
        return modelMapper.map(entity, DescriptionGroupDTO::class.java)
    }

    override fun toEntity(dto: DescriptionGroupDTO): DescriptionGroup {
        return modelMapper.map(dto, DescriptionGroup::class.java)
    }
}