package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.PaymentDTO
import com.example.admp_api_flora_mart.entity.Payment
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class PaymentMapper(private val modelMapper: ModelMapper): Mapper<PaymentDTO, Payment> {
    override fun toDto(entity: Payment): PaymentDTO {
        return modelMapper.map(entity, PaymentDTO::class.java)
    }

    override fun toEntity(dto: PaymentDTO): Payment {
        return modelMapper.map(dto, Payment::class.java)
    }
}