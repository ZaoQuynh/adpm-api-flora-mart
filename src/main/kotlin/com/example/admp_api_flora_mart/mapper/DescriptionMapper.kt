package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.DescriptionDTO
import com.example.admp_api_flora_mart.entity.Description
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class DescriptionMapper(private val modelMapper: ModelMapper): Mapper<DescriptionDTO, Description> {
    override fun toDto(entity: Description): DescriptionDTO {
        return modelMapper.map(entity, DescriptionDTO::class.java)
    }

    override fun toEntity(dto: DescriptionDTO): Description {
        return modelMapper.map(dto, Description::class.java)
    }
}