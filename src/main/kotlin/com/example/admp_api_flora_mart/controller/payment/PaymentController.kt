package com.example.admp_api_flora_mart.controller.payment

import com.example.admp_api_flora_mart.dto.PaymentDTO
import com.example.admp_api_flora_mart.service.OrderItemService
import com.example.admp_api_flora_mart.service.PaymentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/payment")
class PaymentController(
    private val paymentService: PaymentService
){
    @PostMapping
    fun addPayment(@RequestBody paymentDTO: PaymentDTO): ResponseEntity<Any> {
        return try {
            val newOrderItem = paymentService.add(paymentDTO)
            ResponseEntity.ok(newOrderItem)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}