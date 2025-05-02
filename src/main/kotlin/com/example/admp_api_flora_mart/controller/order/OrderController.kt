package com.example.admp_api_flora_mart.controller.order

import com.example.admp_api_flora_mart.controller.order.response.CashflowStats
import com.example.admp_api_flora_mart.controller.order.response.MyOrderResponse
import com.example.admp_api_flora_mart.dto.OrderDTO
import com.example.admp_api_flora_mart.service.OrderService
import com.example.admp_api_flora_mart.service.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/order")
class OrderController(private val orderService: OrderService,
                      private val tokenService: TokenService
) {

    @GetMapping
    fun getOrders(): ResponseEntity<Any>{
        return try {
            val orders = orderService.getOrders();
            ResponseEntity.ok(orders)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable orderId: Long): ResponseEntity<Any>{
        return try {
            val order = orderService.getById(orderId);
            ResponseEntity.ok(order)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @GetMapping("/my-order")
    fun getMyOrders(@RequestHeader("Authorization") token: String): ResponseEntity<Any>{
        return try {
            val jwt = token.removePrefix("Bearer ")
            val email = tokenService.extractEmail(jwt)
            if (email == null) {
                return ResponseEntity.status(401).body(mapOf("error" to "Invalid token"))
            }
            val orders = orderService.getOrdersByUser(email)
            return ResponseEntity.ok(orders)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @GetMapping("/my-order-flow-stats")
    fun getMyOrderFlowStats(@RequestHeader("Authorization") token: String): ResponseEntity<Any> {
        return try {
            val jwt = token.removePrefix("Bearer ")
            val email = tokenService.extractEmail(jwt)
            if (email == null) {
                return ResponseEntity.status(401).body(mapOf("error" to "Invalid token"))
            }

            val response = orderService.calculateCashflowByUser(email)

            return ResponseEntity.ok(response)
        } catch (ex: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PostMapping
    fun addOrder(@RequestBody orderDTO: OrderDTO): ResponseEntity<Any> {
        return try {
            val newOrder = orderService.add(orderDTO)
            ResponseEntity.ok(newOrder)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PatchMapping("/cancel/{orderId}")
    fun cancelOrder(@PathVariable orderId: Long): ResponseEntity<Any> {
        return try {
            val canceledOrder = orderService.cancel(orderId)
            ResponseEntity.ok(canceledOrder)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PatchMapping("/receive/{orderId}")
    fun receiveOrder(@PathVariable orderId: Long): ResponseEntity<Any> {
        return try {
            val receivedOrder = orderService.receive(orderId)
            ResponseEntity.ok(receivedOrder)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PatchMapping("/status/{orderId}")
    fun updateOrderStatus(@PathVariable orderId: Long): ResponseEntity<Any> {
        return try {
            val receivedOrder = orderService.updateOrderStatus(orderId)
            ResponseEntity.ok(receivedOrder)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}