package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.controller.order.response.CashflowStats
import com.example.admp_api_flora_mart.controller.order.response.MyOrderResponse
import com.example.admp_api_flora_mart.controller.order.response.RevenueByYearResponse
import com.example.admp_api_flora_mart.dto.OrderDTO
import com.example.admp_api_flora_mart.entity.EOrderStatus
import com.example.admp_api_flora_mart.entity.Order
import com.example.admp_api_flora_mart.mapper.*
import com.example.admp_api_flora_mart.reponsitory.OrderRepository
import com.example.admp_api_flora_mart.reponsitory.UserRepository
import com.example.admp_api_flora_mart.reponsitory.VoucherRepository
import com.example.admp_api_flora_mart.scheduler.order.OrderSchedulerService
import com.example.admp_api_flora_mart.service.NotificationService
import com.example.admp_api_flora_mart.service.OrderService
import com.example.admp_api_flora_mart.utils.NotificationUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderMapper: OrderMapper,
    private val orderItemMapper: OrderItemMapper,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val voucherMapper: VoucherMapper,
    private val paymentMapper: PaymentMapper,
    private val orderSchedulerService: OrderSchedulerService,
    private val voucherRepository: VoucherRepository,
    private val notificationSevice: NotificationService
): OrderService {


    override fun getOrders(): List<OrderDTO> {
        val products = orderRepository.findAll();
        return products.map { orderMapper.toDto(it) }
    }


    override fun add(orderDTO: OrderDTO): OrderDTO {
        val voucher = voucherRepository.findById(1).orElseThrow()
        val order = Order(
            customer = orderDTO.customer?.let { userMapper.toEntity(it) },
            status = EOrderStatus.NEW,
            createDate = LocalDateTime.now(),
            vouchers =  mutableListOf(voucher),
            payment = orderDTO.payment?.let { paymentMapper.toEntity(it) },
            address = orderDTO.address,
            phone = orderDTO.phone
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
        val order = orderRepository.findById(orderId).orElseThrow {
            IllegalArgumentException("Order with ID $orderId not found")
        }
        val status = order.status;
        when(status){
            EOrderStatus.NEW -> order.status = EOrderStatus.CONFIRMED
            EOrderStatus.CONFIRMED -> order.status = EOrderStatus.PREPARING
            EOrderStatus.PREPARING -> order.status = EOrderStatus.SHIPPING
            EOrderStatus.SHIPPING -> order.status = EOrderStatus.SHIPPED
            else -> throw IllegalStateException("Cannot update status from ${order.status}")
        }
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
        val savedOrder = orderMapper.toDto(orderRepository.save(order))
        val message = NotificationUtils.createOrderNotification(savedOrder.status!!, savedOrder.customer!!,
            savedOrder.id!!
        )
        if(message != null)
            savedOrder.customer!!.id?.let { notificationSevice.sendToUser(it, message) };
        return savedOrder
    }

    override fun receive(orderId: Long): OrderDTO {
        val order = orderRepository.findById(orderId)
            .orElseThrow { Exception("Order not found") }

        if (order.status != EOrderStatus.SHIPPED) {
            throw IllegalStateException("Only shipped order can be received")
        }

        order.status = EOrderStatus.DELIVERED

        val savedOrder = orderMapper.toDto(orderRepository.save(order))
        val message = NotificationUtils.createOrderNotification(savedOrder.status!!, savedOrder.customer!!,
            savedOrder.id!!
        )
        if(message != null)
            savedOrder.customer!!.id?.let { notificationSevice.sendToUser(it, message) };
        return savedOrder
    }

    override fun getById(orderId: Long): OrderDTO {
        return orderMapper.toDto(orderRepository.findById(orderId).orElse(null))
    }

    fun scheduleOrderUpdates(order: Order) {
        val createDate = order.createDate
            ?: throw IllegalArgumentException("createDate not null")

        val updateTime = Date.from(
            createDate.plusMinutes(1)
                .atZone(ZoneId.systemDefault())
                .toInstant()
        )

        order.id?.let { orderId ->
            orderSchedulerService.scheduleOrderStatusUpdate(orderId, updateTime)
        } ?: throw IllegalArgumentException("Order ID not null")
    }
    override fun calculateCashflowByUser(email: String): MyOrderResponse {
        val user = userRepository.findByEmail(email)
            .orElseThrow { Exception("User not found for email: $email.") }
        val orders = orderRepository.findAllByCustomer(user).map { orderMapper.toDto(it) }

        // Group by year
        val cashflowStatsByYear = mutableMapOf<Int, CashflowStats>()

        orders.forEach { order ->
            val itemTotal = order.orderItems.sumOf { (it.qty ?: 0) * (it.discounted ?: 0.0) }
            val voucher = order.vouchers.firstOrNull()

            val finalAmount = if (voucher != null && itemTotal >= voucher.minOrderAmount!!) {
                val discountAmount = itemTotal * (voucher.discount!!)/100
                val cappedDiscount = if (voucher.maxDiscount != null) discountAmount.coerceAtMost(voucher.maxDiscount!!) else discountAmount
                itemTotal - cappedDiscount
            } else {
                itemTotal
            }

            val year = order.createDate?.year ?: 0  // Giả sử order có trường createdAt là Date, lấy năm từ đó
            val stats = cashflowStatsByYear.getOrPut(year) {
                CashflowStats(year, 0.0, 0.0, 0.0) // Tạo một CashflowStats mới cho năm này nếu chưa có
            }

            when (order.status) {
                EOrderStatus.NEW -> stats.pendingAmount += finalAmount
                EOrderStatus.SHIPPING -> stats.shippingAmount += finalAmount
                EOrderStatus.DELIVERED -> stats.deliveredAmount += finalAmount
                else -> {}
            }
        }

        return MyOrderResponse(
            orders = orders,
            statsByYear = cashflowStatsByYear.values.toList() // Chuyển đổi map thành list
        )
    }

    override fun calculateRevenueByYear(): RevenueByYearResponse {
        val orders = orderRepository.findAll().map { orderMapper.toDto(it) }

        // Dữ liệu kết quả: Map<Year, List<12 tháng doanh thu>>
        val revenueMap = mutableMapOf<Int, MutableList<Double>>()

        orders.forEach { order ->
            val createdAt = order.createDate ?: return@forEach
            val year = createdAt.year
            val month = createdAt.monthValue - 1 // từ 0 đến 11

            val itemTotal = order.orderItems.sumOf { (it.qty ?: 0) * (it.discounted ?: 0.0) }
            val voucher = order.vouchers.firstOrNull()

            val finalAmount = if (voucher != null && itemTotal >= voucher.minOrderAmount!!) {
                val discountAmount = itemTotal * (voucher.discount!!)/100
                val cappedDiscount = if (voucher.maxDiscount != null) discountAmount.coerceAtMost(voucher.maxDiscount!!) else discountAmount
                itemTotal - cappedDiscount
            } else {
                itemTotal
            }

            // Chỉ tính những đơn đã giao thành công
            if (order.status == EOrderStatus.DELIVERED) {
                val monthRevenue = revenueMap.getOrPut(year) { MutableList(12) { 0.0 } }
                monthRevenue[month] += finalAmount/1000
            }
        }

        return RevenueByYearResponse(revenueDataByYear = revenueMap)
    }
}