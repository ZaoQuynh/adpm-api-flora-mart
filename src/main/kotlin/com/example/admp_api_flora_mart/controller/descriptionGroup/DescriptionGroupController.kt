package com.example.admp_api_flora_mart.controller.descriptionGroup

import com.example.admp_api_flora_mart.service.DescriptionGroupService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/description-group")
class DescriptionGroupController(private val descriptionGroupService: DescriptionGroupService) {

    @GetMapping
    fun getDescriptionGroups(): ResponseEntity<Any> {
        return try {
            val descriptionGroups = descriptionGroupService.getDescriptionGroups();
            ResponseEntity.ok(descriptionGroups)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}