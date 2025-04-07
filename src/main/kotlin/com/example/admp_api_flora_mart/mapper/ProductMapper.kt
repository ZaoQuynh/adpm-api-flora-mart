package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.ProductDTO
import com.example.admp_api_flora_mart.entity.Product
import com.example.admp_api_flora_mart.service.OrderItemService
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class ProductMapper(private val modelMapper: ModelMapper,
    private val orderItemService: OrderItemService): Mapper<ProductDTO, Product> {
    override fun toDto(entity: Product): ProductDTO {
        var dto = modelMapper.map(entity, ProductDTO::class.java)
        dto.soldQty = orderItemService.soldQtyByProductId(entity.id)
        dto.stockQty = entity.stockQty?.minus(dto.soldQty!!)
        return dto
    }

    override fun toEntity(dto: ProductDTO): Product {
        return modelMapper.map(dto, Product::class.java)
    }
}