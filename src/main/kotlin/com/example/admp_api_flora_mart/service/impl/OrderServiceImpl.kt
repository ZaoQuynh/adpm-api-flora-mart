package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.OrderDTO
import com.example.admp_api_flora_mart.entity.EOrderStatus
import com.example.admp_api_flora_mart.entity.Order
import com.example.admp_api_flora_mart.mapper.*
import com.example.admp_api_flora_mart.reponsitory.OrderRepository
import com.example.admp_api_flora_mart.reponsitory.UserRepository
import com.example.admp_api_flora_mart.scheduler.order.OrderSchedulerService
import com.example.admp_api_flora_mart.service.OrderService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.naming.AuthenticationException

@Service
class OrderServiceImpl(private val orderRepository: OrderRepository,
    private val orderMapper: OrderMapper,
    private val orderItemMapper: OrderItemMapper,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val voucherMapper: VoucherMapper,
    private val paymentMapper: PaymentMapper,
    private val orderSchedulerService: OrderSchedulerService): OrderService {


    override fun getOrders(): List<OrderDTO> {
        val products = orderRepository.findAll();
        return products.map { orderMapper.toDto(it) }
    }


    override fun add(orderDTO: OrderDTO): OrderDTO {
        val order = Order(
            customer = orderDTO.customer?.let { userMapper.toEntity(it) },
            status = EOrderStatus.NEW,
            createDate = LocalDateTime.now(),
            vouchers = orderDTO.vouchers.map { voucherMapper.toEntity(it) }.toMutableList(),
            payment = orderDTO.payment?.let { paymentMapper.toEntity(it) },
            address = orderDTO.address
        )
        val savedOrder = orderRepository.save(order)

        val orderItems = orderDTO.orderItems.map {
            val orderItem = orderItemMapper.toEntity(it)
            orderItem.order = savedOrder
            orderItem
        }.toMutableList()

        savedOrder.orderItems.addAll(orderItems)
        val finalSavedOrder = orderRepository.save(savedOrder)

        scheduleOrderUpdates(finalSavedOrder)

        return orderMapper.toDto(finalSavedOrder)
    }

    override fun getOrdersByUser(email: String): List<OrderDTO> {
        val user = userRepository.findByEmail(email)
            .orElseThrow { Exception("User not found for email: $email.") }
        return orderRepository.findAllByCustomer(user).map { orderMapper.toDto(it)}
    }

    override fun updateOrderStatus(orderId: Long): OrderDTO {
        val order = orderRepository.findById(orderId).orElse(null)
        order.status = EOrderStatus.CONFIRMED
        return orderMapper.toDto(orderRepository.save(order))
    }

    override fun cancel(orderId: Long): OrderDTO {
        val order = orderRepository.findById(orderId)
            .orElseThrow { Exception("Order not found") }

        if (order.status != EOrderStatus.NEW) {
            throw IllegalStateException("Only new orders can be canceled")
        }

        order.status = EOrderStatus.CANCELED
        order.id?.let { orderSchedulerService.cancelScheduledOrderUpdate(it) }
        return orderMapper.toDto(orderRepository.save(order))
    }

    override fun receive(orderId: Long): OrderDTO {
        val order = orderRepository.findById(orderId)
            .orElseThrow { Exception("Order not found") }

        if (order.status != EOrderStatus.SHIPPED) {
            throw IllegalStateException("Only shipped order can be received")
        }

        order.status = EOrderStatus.DELIVERED
        return orderMapper.toDto(orderRepository.save(order))
    }

    override fun getById(orderId: Long): OrderDTO {
        return orderMapper.toDto(orderRepository.findById(orderId).orElse(null))
    }

    fun scheduleOrderUpdates(order: Order) {
        val createDate = order.createDate
            ?: throw IllegalArgumentException("createDate not null")

        val updateTime = Date.from(
            createDate.plusMinutes(30)
                .atZone(ZoneId.systemDefault())
                .toInstant()
        )

        order.id?.let { orderId ->
            orderSchedulerService.scheduleOrderStatusUpdate(orderId, updateTime)
        } ?: throw IllegalArgumentException("Order ID not null")
    }

}