package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.PaymentDTO
import com.example.admp_api_flora_mart.entity.EPaymentStatus
import com.example.admp_api_flora_mart.entity.Payment
import com.example.admp_api_flora_mart.mapper.PaymentMapper
import com.example.admp_api_flora_mart.reponsitory.PaymentRepository
import com.example.admp_api_flora_mart.service.PaymentService
import org.springframework.stereotype.Service

@Service
class PaymentServiceImpl(
    private val paymentReponsitory: PaymentRepository,
    private val paymentMapper: PaymentMapper
): PaymentService {
    override fun add(paymentDTO: PaymentDTO): PaymentDTO {
        val payment = Payment(
            id = paymentDTO.id,
            paymentDate = paymentDTO.paymentDate,
            type = paymentDTO.type,
            status = EPaymentStatus.PENDING,
        )
        val savedPayment = paymentReponsitory.save(payment)
        return paymentMapper.toDto(savedPayment)
    }
}