package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.controller.orderItem.request.AddToCartRequest
import com.example.admp_api_flora_mart.dto.CartDTO
import com.example.admp_api_flora_mart.dto.OrderItemDTO
import com.example.admp_api_flora_mart.entity.EOrderStatus
import com.example.admp_api_flora_mart.entity.OrderItem
import com.example.admp_api_flora_mart.mapper.OrderItemMapper
import com.example.admp_api_flora_mart.reponsitory.CartRepository
import com.example.admp_api_flora_mart.reponsitory.OrderItemRepository
import com.example.admp_api_flora_mart.reponsitory.ProductRepository
import com.example.admp_api_flora_mart.service.OrderItemService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderItemServiceImpl(
    private val orderItemRepository: OrderItemRepository,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val orderItemMapper: OrderItemMapper,
) : OrderItemService {

    @Transactional
    override fun updateItemQuantity(itemId: Long, qty: Int): OrderItemDTO {
        val orderItem = orderItemRepository.findById(itemId)
            .orElseThrow { RuntimeException("Order item not found") }

        orderItem.qty = qty
        return orderItemMapper.toDto(orderItem)
    }

    @Transactional
    override fun removeItemFromCart(itemId: Long) {
        val orderItem = orderItemRepository.findById(itemId)
            .orElseThrow { RuntimeException("Order item not found") }

        orderItem.cart?.orderItems?.remove(orderItem)
        orderItemRepository.delete(orderItem)
    }

    override fun soldQtyByProductId(id: Long?): Int {
        if (id == null) return 0
        val orderItems = orderItemRepository.findByProductId(id)
        val completedOrderItems = orderItems.filter { orderItem ->
            orderItem.order?.status == EOrderStatus.DELIVERED
        }
        return completedOrderItems.sumOf { it.qty ?: 0 }
    }


    @Transactional
    override fun addToCart(request: AddToCartRequest): OrderItemDTO {
        val productId = request.productDTO.id
            ?: throw RuntimeException("Product ID is required")
        val product = productRepository.findById(productId)
            .orElseThrow { RuntimeException("Product not found with ID: $productId") }

        val cart = request.cartDTO.id?.let {
            cartRepository.findById(it).orElseThrow { RuntimeException("Cart not found") }
        } ?: throw RuntimeException("Cart ID is null")

        val existingOrderItem = cart.orderItems.find { it.product?.id == productId }

        return if (existingOrderItem != null) {
            existingOrderItem.qty = (existingOrderItem.qty ?: 0) + 1
            existingOrderItem.currentPrice = product.price
            existingOrderItem.discounted = product.discount

            val updatedOrderItem = orderItemRepository.save(existingOrderItem)

            if (cart.orderItems == null) {
                cart.orderItems = mutableListOf()
            }

            cart.orderItems.add(updatedOrderItem)
            cartRepository.saveAndFlush(cart)
            orderItemMapper.toDto(updatedOrderItem)
        } else {
            val newOrderItem = OrderItem(
                product = product,
                discounted = product.discount,
                qty = 1,
                currentPrice = product.price,
                cart = cart
            )

            val savedOrderItem = orderItemRepository.save(newOrderItem)

            if (cart.orderItems == null) {
                cart.orderItems = mutableListOf()
            }

            cart.orderItems.add(savedOrderItem)
            cartRepository.saveAndFlush(cart)
            orderItemMapper.toDto(savedOrderItem)
        }
    }
}
