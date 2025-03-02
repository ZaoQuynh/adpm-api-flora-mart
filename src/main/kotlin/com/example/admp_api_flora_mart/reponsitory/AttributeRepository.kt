package com.example.admp_api_flora_mart.reponsitory

import com.example.admp_api_flora_mart.entity.Attribute
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AttributeRepository: JpaRepository<Attribute, Long> {
    fun existsByName(name: String?): Boolean
}