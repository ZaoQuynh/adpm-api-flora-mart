package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.VoucherTypeDTO
import com.example.admp_api_flora_mart.entity.VoucherType
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class VoucherTypeMapper(private val modelMapper: ModelMapper): Mapper<VoucherTypeDTO, VoucherType> {
    override fun toDto(entity: VoucherType): VoucherTypeDTO {
        return modelMapper.map(entity, VoucherTypeDTO::class.java)
    }

    override fun toEntity(dto: VoucherTypeDTO): VoucherType {
        return modelMapper.map(dto, VoucherType::class.java)
    }

}