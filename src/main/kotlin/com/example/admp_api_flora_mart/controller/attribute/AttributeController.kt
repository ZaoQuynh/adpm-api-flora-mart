package com.example.admp_api_flora_mart.controller.attribute

import com.example.admp_api_flora_mart.controller.attribute.request.AddAttributeRequest
import com.example.admp_api_flora_mart.dto.AttributeDTO
import com.example.admp_api_flora_mart.entity.Attribute
import com.example.admp_api_flora_mart.service.AttributeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/attribute")
class AttributeController(private val attributeService: AttributeService) {

    @GetMapping
    fun getAttributes(): ResponseEntity<Any>{
        return try {
            val attributes = attributeService.getAttributes();
            ResponseEntity.ok(attributes)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PostMapping
    fun addAttribute(@RequestBody addAttributeRequest: AddAttributeRequest): ResponseEntity<Any>{
        return try {
            val newAttribute = attributeService.add(addAttributeRequest.attribute, addAttributeRequest.attributeGroup)
            ResponseEntity.ok(newAttribute)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}