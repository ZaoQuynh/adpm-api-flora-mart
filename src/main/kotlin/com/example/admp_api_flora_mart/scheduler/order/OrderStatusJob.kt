package com.example.admp_api_flora_mart.scheduler.order

import com.example.admp_api_flora_mart.service.OrderService
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class OrderStatusJob(
    private val orderService: OrderService
) : Job {
    override fun execute(context: JobExecutionContext) {
        val orderId = context.jobDetail.jobDataMap.getLong("orderId")
        orderService.updateOrderStatus(orderId)
    }
}