package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.dto.PlantDTO

interface PlantService {
    fun add(plantDTO: PlantDTO): PlantDTO
    fun update(id: Long, plantDTO: PlantDTO): PlantDTO
}