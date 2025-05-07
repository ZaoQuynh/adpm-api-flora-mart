package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.dto.VoucherDTO

interface VoucherService {
    fun add(voucherDTO: VoucherDTO): VoucherDTO
}