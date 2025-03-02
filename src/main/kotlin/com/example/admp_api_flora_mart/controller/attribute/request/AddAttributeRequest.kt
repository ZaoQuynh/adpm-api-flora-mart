package com.example.admp_api_flora_mart.controller.attribute.request

import com.example.admp_api_flora_mart.dto.AttributeDTO
import com.example.admp_api_flora_mart.dto.AttributeGroupDTO

data class AddAttributeRequest(
    var attribute: AttributeDTO?= null,
    var attributeGroup: AttributeGroupDTO? = null,
)
