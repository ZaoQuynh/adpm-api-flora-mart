package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.dto.DescriptionGroupDTO
import com.example.admp_api_flora_mart.entity.DescriptionGroup

interface DescriptionGroupService {
    fun getDescriptionGroups(): List<DescriptionGroupDTO>
}