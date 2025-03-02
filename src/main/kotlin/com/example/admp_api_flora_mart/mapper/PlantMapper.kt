package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.PlantDTO
import com.example.admp_api_flora_mart.entity.Plant
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class PlantMapper(private val modelMapper: ModelMapper): Mapper<PlantDTO, Plant> {
    override fun toDto(entity: Plant): PlantDTO {
        return modelMapper.map(entity, PlantDTO::class.java)
    }

    override fun toEntity(dto: PlantDTO): Plant {
        return modelMapper.map(dto, Plant::class.java)
    }
}