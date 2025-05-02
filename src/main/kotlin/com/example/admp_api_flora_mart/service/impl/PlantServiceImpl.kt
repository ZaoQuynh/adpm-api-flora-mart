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

    override fun update(id: Long, plantDTO: PlantDTO): PlantDTO {
        val existingPlant = plantRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Plant with id $id not found") }

        existingPlant.name = plantDTO.name
        existingPlant.img = plantDTO.img
        existingPlant.descriptions = plantDTO.descriptions.map { descriptionMapper.toEntity(it) }.toMutableList()
        existingPlant.attributes = plantDTO.attributes.map { attributeMapper.toEntity(it) }.toMutableList()

        val updatedPlant = plantRepository.save(existingPlant)
        return plantMapper.toDto(updatedPlant)
    }
}