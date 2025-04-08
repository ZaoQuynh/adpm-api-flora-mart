package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.controller.cart.request.CheckoutRequest
import com.example.admp_api_flora_mart.controller.cart.request.UpdateCartQuantityRequest
import com.example.admp_api_flora_mart.dto.CartDTO
import com.example.admp_api_flora_mart.entity.*
import com.example.admp_api_flora_mart.mapper.CartMapper
import com.example.admp_api_flora_mart.mapper.OrderItemMapper
import com.example.admp_api_flora_mart.mapper.UserMapper
import com.example.admp_api_flora_mart.reponsitory.*
import com.example.admp_api_flora_mart.service.CartService

import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val orderItemMapper: OrderItemMapper,
    private val cartMapper: CartMapper,
    private val orderItemRepository: OrderItemRepository,
    private val orderRepository: OrderRepository,
    private val paymentRepository: PaymentRepository,
    private val voucherRepository: VoucherRepository
): CartService {

    override fun getCartByUser(email: String): CartDTO {
        val user = userRepository.findByEmail(email)
            .orElseThrow { Exception("User not found for email: $email.") }

        val cart = cartRepository.findByCustomerId(user.id!!)
            .orElseThrow { Exception("Cart not found for user: $email.") }

        return cartMapper.toDto(cart)
    }

    override fun add(cartDTO: CartDTO): CartDTO {
        val cart = Cart(
            customer = cartDTO.customer?.let { userMapper.toEntity(it) }
                ?: throw IllegalArgumentException("Customer cannot be null")
        )

        val savedCart = cartRepository.save(cart)

        val orderItems = cartDTO.orderItems.map {
            val orderItem = orderItemMapper.toEntity(it)
            orderItem.cart = savedCart
            orderItem
        }.toMutableList()

        savedCart.orderItems.addAll(orderItems)
        val finalSavedCart = cartRepository.save(savedCart)

        return cartMapper.toDto(finalSavedCart)
    }

    override fun checkOut(request: CheckoutRequest): CartDTO {
        val vouchers = if (request.voucherId != 0L) {
            val voucher = voucherRepository.findById(request.voucherId)
                .orElseThrow { RuntimeException("Voucher not found") }
            mutableListOf(voucher)
        } else {
            mutableListOf()
        }
        val cart = request.cartDTO.id
            ?.let { cartRepository.findById(it).orElseThrow { RuntimeException("Cart not found") } }
            ?: throw RuntimeException("Cart ID is null")

        val validOrderItems = cart.orderItems.filter { it.qty!! > 0 }

        if (validOrderItems.isEmpty()) {
            throw RuntimeException("No valid items in the cart to checkout")
        }
        val payment = Payment(
            paymentDate = LocalDateTime.now(),
            type = request.type,
            status = EPaymentStatus.PENDING,
        )
        val savedPayment = paymentRepository.save(payment)

        val newOrder = Order(
            customer = cart.customer,
            status = EOrderStatus.NEW,
            createDate = LocalDateTime.now(),
            vouchers = vouchers,
            payment = payment,
            address = request.address
        )

        val savedOrder = orderRepository.save(newOrder)

        validOrderItems.forEach { orderItem ->
            orderItem.order = savedOrder
            orderItem.cart = null
        }
        orderItemRepository.saveAll(validOrderItems)

        return request.cartDTO
    }

    override fun updateQuantity(request: UpdateCartQuantityRequest): CartDTO {
        val cart = cartRepository.findById(request.cartId)
            .orElseThrow { throw IllegalArgumentException("Cart not found") }

        val orderItem = cart.orderItems.find { it.product?.id == request.productId }
            ?: throw IllegalArgumentException("Product not found in cart")

        if (request.quantity <= 0) {
            cart.orderItems.remove(orderItem)
        } else {
            orderItem.qty = request.quantity
            orderItemRepository.save(orderItem)
        }

        val updatedCart = cartRepository.save(cart)
        return cartMapper.toDto(updatedCart)
    }
}
