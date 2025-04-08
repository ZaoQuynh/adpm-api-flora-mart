package com.example.admp_api_flora_mart.controller.voucher

import com.example.admp_api_flora_mart.dto.VoucherDTO
import com.example.admp_api_flora_mart.reponsitory.VoucherRepository
import com.example.admp_api_flora_mart.service.VoucherService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/voucher")
class VoucherController(private val voucherService: VoucherService,
    private val voucherRepository: VoucherRepository
) {
    @PostMapping
    fun addVoucher(@RequestBody voucher: VoucherDTO): ResponseEntity<Any> {
        return try {
            val newVoucher = voucherService.add(voucher)
            ResponseEntity.ok(newVoucher)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @GetMapping("/{userId}")
    fun getOrderById(@PathVariable userId: Long): ResponseEntity<Any>{
        return try {
            val order = voucherRepository.findUsedVouchersByUser(userId);
            ResponseEntity.ok(order)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}