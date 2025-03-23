package com.example.admp_api_flora_mart.controller.orderItem

import com.example.admp_api_flora_mart.controller.orderItem.request.AddToCartRequest
import com.example.admp_api_flora_mart.service.OrderItemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/cart") // Đổi tên endpoint cho rõ ràng hơn
class OrderItemController(private val orderItemService: OrderItemService) {

    @PostMapping("/add")
    fun addToCart(@RequestBody request: AddToCartRequest): ResponseEntity<Any> {
        return try {
            val order = orderItemService.addToCart(request)
            ResponseEntity.ok(order)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }
}
