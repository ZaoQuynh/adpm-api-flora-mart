package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.CartDTO
import com.example.admp_api_flora_mart.dto.OrderItemDTO
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

    @Transactional
    override fun addToCart(orderItemDTO: OrderItemDTO, cartDTO: CartDTO): OrderItemDTO {
        val productId = orderItemDTO.product?.id
            ?: throw RuntimeException("Product ID is required")
        val product = productRepository.findById(productId)
            .orElseThrow { RuntimeException("Product not found with ID: $productId") }

        val cart = cartDTO.id?.let {
            cartRepository.findById(it).orElseThrow { RuntimeException("Cart not found") }
        } ?: throw RuntimeException("Cart ID is null")

        val existingOrderItem = cart.orderItems.find { it.product?.id == productId }

        return if (existingOrderItem != null) {
            existingOrderItem.qty = (existingOrderItem.qty ?: 0) + (orderItemDTO.qty ?: 1)
            existingOrderItem.currentPrice = orderItemDTO.currentPrice
            existingOrderItem.discounted = orderItemDTO.discounted

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
                discounted = orderItemDTO.discounted,
                qty = orderItemDTO.qty ?: 1,
                currentPrice = orderItemDTO.currentPrice,
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
