package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.OrderDTO
import com.example.admp_api_flora_mart.entity.Order
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class OrderMapper(private val modelMapper: ModelMapper): Mapper<OrderDTO, Order> {
    override fun toDto(entity: Order): OrderDTO {
        return modelMapper.map(entity, OrderDTO::class.java)
    }

    override fun toEntity(dto: OrderDTO): Order {
        return modelMapper.map(dto, Order::class.java)
    }

}