package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.ProductDTO
import com.example.admp_api_flora_mart.entity.Product
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class ProductMapper(private val modelMapper: ModelMapper): Mapper<ProductDTO, Product> {
    override fun toDto(entity: Product): ProductDTO {
        return modelMapper.map(entity, ProductDTO::class.java)
    }

    override fun toEntity(dto: ProductDTO): Product {
        return modelMapper.map(dto, Product::class.java)
    }
}