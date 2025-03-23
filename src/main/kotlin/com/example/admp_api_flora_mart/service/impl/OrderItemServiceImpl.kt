package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.controller.orderItem.request.AddToCartRequest
import com.example.admp_api_flora_mart.dto.OrderItemDTO
import com.example.admp_api_flora_mart.entity.*
import com.example.admp_api_flora_mart.mapper.OrderItemMapper
import com.example.admp_api_flora_mart.reponsitory.OrderItemRepository
import com.example.admp_api_flora_mart.reponsitory.OrderRepository
import com.example.admp_api_flora_mart.reponsitory.ProductRepository
import com.example.admp_api_flora_mart.reponsitory.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderItemServiceImpl(
    private val orderItemRepository: OrderItemRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
    private val orderItemMapper: OrderItemMapper,
    private val orderRepository: OrderRepository,
): OrderItemService {
    override fun addToCart(request: AddToCartRequest): OrderItemDTO {
        val customer = userRepository.findById(request.customerId).orElseThrow {
            IllegalArgumentException("User not found")
        }
        val product = productRepository.findById(request.productId).orElseThrow {
            IllegalArgumentException("Product not found")
        }

        var order = orderRepository.findUnpaidOrderByUser(customer.id!!)

        if (order == null) {
            order = Order(
                customer = customer,
                status = EOrderStatus.NEW,
                createDate = LocalDateTime.now(),
                orderItems = mutableListOf(),
                payment = null
            )
            order = orderRepository.save(order)
        }

        val existingItem = orderItemRepository.findByOrderCustomerAndProduct(customer.id!!, product.id!!)

        val orderItem = if (existingItem != null) {
            existingItem.qty = (existingItem.qty ?: 0) + request.quantity
            orderItemRepository.save(existingItem)
        } else {
            val newItem = OrderItem(
                product = product,
                qty = request.quantity,
                currentPrice = product.price,
                isReviewed = false,
                order = order
            )
            orderItemRepository.save(newItem)
        }

        return orderItemMapper.toDto(orderItem)
    }
    override fun getUserCart(userId: Long): List<OrderItemDTO> {
        val cartItems = orderItemRepository.findUnpaidOrderItemsByUser(userId)
        return cartItems.map { orderItemMapper.toDto(it) }
    }
}
