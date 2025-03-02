package com.example.admp_api_flora_mart.reponsitory

import com.example.admp_api_flora_mart.entity.AttributeGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AttributeGroupRepository: JpaRepository<AttributeGroup, Long> {
    fun existsByName(name: String?): Boolean
}