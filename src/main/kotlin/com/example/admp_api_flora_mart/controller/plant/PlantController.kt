package com.example.admp_api_flora_mart.controller.plant

import com.example.admp_api_flora_mart.controller.attribute.request.AddAttributeRequest
import com.example.admp_api_flora_mart.dto.PlantDTO
import com.example.admp_api_flora_mart.service.PlantService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/plant")
class PlantController(private val plantService: PlantService) {

    @PostMapping
    fun addPlant(@RequestBody plantDTO: PlantDTO): ResponseEntity<Any> {
        return try {
            val newPlant = plantService.add(plantDTO)
            ResponseEntity.ok(newPlant)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}