package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.DescriptionGroupDTO
import com.example.admp_api_flora_mart.entity.DescriptionGroup
import com.example.admp_api_flora_mart.mapper.AttributeGroupMapper
import com.example.admp_api_flora_mart.mapper.DescriptionGroupMapper
import com.example.admp_api_flora_mart.reponsitory.AttributeGroupRepository
import com.example.admp_api_flora_mart.reponsitory.DescriptionGroupRepository
import com.example.admp_api_flora_mart.service.DescriptionGroupService
import org.springframework.stereotype.Service

@Service
class DescriptionGroupServiceImpl(
    private val descriptionGroupRepository: DescriptionGroupRepository,
    private val descriptionGroupMapper: DescriptionGroupMapper
): DescriptionGroupService{
    override fun getDescriptionGroups(): List<DescriptionGroupDTO> {
        val descriptionGroups = descriptionGroupRepository.findAll();
        return descriptionGroups.map { descriptionGroupMapper.toDto(it) }
    }
}