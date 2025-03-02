package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.VoucherDTO
import com.example.admp_api_flora_mart.entity.Voucher
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class VoucherMapper(private val modelMapper: ModelMapper): Mapper<VoucherDTO, Voucher> {
    override fun toDto(entity: Voucher): VoucherDTO {
        return modelMapper.map(entity, VoucherDTO::class.java)
    }

    override fun toEntity(dto: VoucherDTO): Voucher {
        return modelMapper.map(dto, Voucher::class.java)
    }
}