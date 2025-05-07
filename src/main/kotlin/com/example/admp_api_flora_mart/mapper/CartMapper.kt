package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.CartDTO
import com.example.admp_api_flora_mart.entity.Cart
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class CartMapper(private val modelMapper: ModelMapper): Mapper<CartDTO, Cart> {
    override fun toDto(entity: Cart): CartDTO {
        return modelMapper.map(entity, CartDTO::class.java)
    }

    override fun toEntity(dto: CartDTO): Cart {
        return modelMapper.map(dto, Cart::class.java)
    }

}