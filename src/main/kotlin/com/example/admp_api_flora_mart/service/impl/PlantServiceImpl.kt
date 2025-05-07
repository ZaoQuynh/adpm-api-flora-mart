package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.PlantDTO
import com.example.admp_api_flora_mart.entity.Attribute
import com.example.admp_api_flora_mart.entity.Description
import com.example.admp_api_flora_mart.entity.Plant
import com.example.admp_api_flora_mart.mapper.AttributeMapper
import com.example.admp_api_flora_mart.mapper.DescriptionMapper
import com.example.admp_api_flora_mart.mapper.PlantMapper
import com.example.admp_api_flora_mart.reponsitory.AttributeRepository
import com.example.admp_api_flora_mart.reponsitory.DescriptionRepository
import com.example.admp_api_flora_mart.reponsitory.PlantRepository
import com.example.admp_api_flora_mart.service.PlantService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlantServiceImpl(
    private val plantRepository: PlantRepository,
    private val descriptionRepository: DescriptionRepository,
    private val attributeRepository: AttributeRepository,
    private val plantMapper: PlantMapper,
    private val descriptionMapper: DescriptionMapper,
    private val attributeMapper: AttributeMapper
) : PlantService {

    @Transactional
    override fun add(plantDTO: PlantDTO): PlantDTO {
        // Process descriptions - check for existing ones first
        val descriptions = processDescriptions(plantDTO)

        // Process attributes - check for existing ones first
        val attributes = processAttributes(plantDTO)

        val plant = Plant(
            name = plantDTO.name,
            descriptions = descriptions,
            attributes = attributes,
            img = plantDTO.img
        )

        val savedPlant = plantRepository.save(plant)
        return plantMapper.toDto(savedPlant)
    }

    @Transactional
    override fun update(id: Long, plantDTO: PlantDTO): PlantDTO {
        val existingPlant = plantRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Plant with id $id not found") }

        // Process descriptions and attributes
        val descriptions = processDescriptions(plantDTO)
        val attributes = processAttributes(plantDTO)

        existingPlant.name = plantDTO.name
        existingPlant.img = plantDTO.img
        existingPlant.descriptions.clear()
        existingPlant.descriptions.addAll(descriptions)
        existingPlant.attributes.clear()
        existingPlant.attributes.addAll(attributes)

        val updatedPlant = plantRepository.save(existingPlant)
        return plantMapper.toDto(updatedPlant)
    }

    private fun processDescriptions(plantDTO: PlantDTO): MutableList<Description> {
        val descriptions = mutableListOf<Description>()

        plantDTO.descriptions.forEach { descDto ->
            val description = descriptionMapper.toEntity(descDto)

            // If the description has an ID, try to find it in the database
            if (description.id != null) {
                val existingDesc = descriptionRepository.findById(description.id!!)
                if (existingDesc.isPresent) {
                    descriptions.add(existingDesc.get())
                    return@forEach
                }
            }

            // Otherwise check if similar description exists (optional, based on your business logic)
            // This is just an example - you might want to check by name or other criteria
            description.id = null // Ensure we're creating a new entity
            descriptions.add(description)
        }

        return descriptions
    }

    private fun processAttributes(plantDTO: PlantDTO): MutableList<Attribute> {
        val attributes = mutableListOf<Attribute>()

        plantDTO.attributes.forEach { attrDto ->
            val attribute = attributeMapper.toEntity(attrDto)

            // If the attribute has an ID, try to find it in the database
            if (attribute.id != null) {
                val existingAttr = attributeRepository.findById(attribute.id!!)
                if (existingAttr.isPresent) {
                    attributes.add(existingAttr.get())
                    return@forEach
                }
            }

            // Otherwise check if similar attribute exists (optional)
            // This is just an example - you might want to check by name or other criteria
            attribute.id = null // Ensure we're creating a new entity
            attributes.add(attribute)
        }

        return attributes
    }
}