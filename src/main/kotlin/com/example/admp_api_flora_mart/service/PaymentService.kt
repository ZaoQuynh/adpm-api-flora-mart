package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.dto.PaymentDTO

interface PaymentService {
    //fun getPaymentById(paymentId: Long): PaymentDTO?
    fun add(paymentDTO: PaymentDTO): PaymentDTO
}