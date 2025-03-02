package com.example.admp_api_flora_mart.controller.attributeGroup

import com.example.admp_api_flora_mart.dto.AttributeDTO
import com.example.admp_api_flora_mart.dto.AttributeGroupDTO
import com.example.admp_api_flora_mart.service.AttributeGroupService
import com.example.admp_api_flora_mart.service.AttributeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/attribute-group")
class AttributeGroupController(private val attributeGroupService: AttributeGroupService) {

    @PostMapping
    fun addAttributeGroup(@RequestBody attributeGroup: AttributeGroupDTO): ResponseEntity<Any> {
        return try {
            val newAttributeGroup = attributeGroupService.add(attributeGroup)
            ResponseEntity.ok(newAttributeGroup)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}