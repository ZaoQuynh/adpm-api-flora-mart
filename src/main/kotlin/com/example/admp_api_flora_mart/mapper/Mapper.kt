package com.example.admp_api_flora_mart.mapper

interface Mapper<D, E> {
    fun toDto(entity: E): D
    fun toEntity(dto: D): E
}