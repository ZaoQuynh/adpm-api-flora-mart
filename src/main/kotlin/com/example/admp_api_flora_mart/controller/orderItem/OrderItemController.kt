package com.example.admp_api_flora_mart.controller.orderItem

import com.example.admp_api_flora_mart.controller.orderItem.request.AddToCartRequest
import com.example.admp_api_flora_mart.service.OrderItemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/orderItem")
class OrderItemController(
    private val orderItemService: OrderItemService,
){
    @PostMapping
    fun addOrderItem(@RequestBody request: AddToCartRequest): ResponseEntity<Any> {
        return try {
            val newOrderItem = orderItemService.addToCart(request)
            ResponseEntity.ok(newOrderItem)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}
