package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.OrderItemDTO
import com.example.admp_api_flora_mart.entity.OrderItem
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class OrderItemMapper(private val modelMapper: ModelMapper): Mapper<OrderItemDTO, OrderItem> {
    override fun toDto(entity: OrderItem): OrderItemDTO {
        return modelMapper.map(entity, OrderItemDTO::class.java)
    }

    override fun toEntity(dto: OrderItemDTO): OrderItem {
        return modelMapper.map(dto, OrderItem::class.java)
    }
}