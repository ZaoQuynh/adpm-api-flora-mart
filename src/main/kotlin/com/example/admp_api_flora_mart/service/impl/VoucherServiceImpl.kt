package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.VoucherDTO
import com.example.admp_api_flora_mart.entity.Voucher
import com.example.admp_api_flora_mart.mapper.*
import com.example.admp_api_flora_mart.reponsitory.VoucherRepository
import com.example.admp_api_flora_mart.service.VoucherService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
@Service
class VoucherServiceImpl(private val voucherRepository: VoucherRepository,
                       private val voucherMapper: VoucherMapper,
                       private val voucherTypeMapper: VoucherTypeMapper,
): VoucherService {
    override fun add(voucherDTO: VoucherDTO): VoucherDTO {
        val voucher = Voucher(
            code = voucherDTO.code,
            discount = voucherDTO.discount,
            createDate = LocalDateTime.now(),
            endDate = voucherDTO.endDate,
            type = voucherDTO.type?.let {voucherTypeMapper.toEntity(it)},
            description = voucherDTO.description,
            minOrderAmount = voucherDTO.minOrderAmount,
            maxDiscount = voucherDTO.maxDiscount,
        )

        val savedVoucher = voucherRepository.save(voucher)
        return voucherMapper.toDto(savedVoucher)
    }
}