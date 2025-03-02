package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.PlantDTO
import com.example.admp_api_flora_mart.entity.Plant
import com.example.admp_api_flora_mart.mapper.AttributeMapper
import com.example.admp_api_flora_mart.mapper.DescriptionMapper
import com.example.admp_api_flora_mart.mapper.PlantMapper
import com.example.admp_api_flora_mart.reponsitory.PlantRepository
import com.example.admp_api_flora_mart.service.PlantService
import org.springframework.stereotype.Service

@Service
class PlantServiceImpl(private val plantRepository: PlantRepository,
    private val plantMapper: PlantMapper,
    private val descriptionMapper: DescriptionMapper,
    private val attributeMapper: AttributeMapper
): PlantService {
    override fun add(plantDTO: PlantDTO): PlantDTO {

        val plant = Plant(
            name = plantDTO.name,
            descriptions = plantDTO.descriptions.map { descriptionMapper.toEntity(it) }.toMutableList(),
            attributes = plantDTO.attributes.map { attributeMapper.toEntity(it) }.toMutableList(),
            img = plantDTO.img
        )

        val savedPlant = plantRepository.save(plant)
        return plantMapper.toDto(savedPlant)
    }
}