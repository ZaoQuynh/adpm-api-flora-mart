package com.example.admp_api_flora_mart.scheduler.order

import org.quartz.*
import org.springframework.stereotype.Service
import java.util.Date

@Service
class OrderSchedulerService(
    private val scheduler: Scheduler
) {

    fun scheduleOrderStatusUpdate(orderId: Long, runAt: Date) {
        val jobDetail = JobBuilder.newJob(OrderStatusJob::class.java)
            .withIdentity("OrderJob_$orderId")
            .usingJobData("orderId", orderId)
            .storeDurably()
            .build()

        val trigger = TriggerBuilder.newTrigger()
            .withIdentity("OrderTrigger_$orderId")
            .startAt(runAt)
            .build()

        scheduler.scheduleJob(jobDetail, trigger)
    }

    fun cancelScheduledOrderUpdate(orderId: Long): Boolean {
        val jobKey = JobKey.jobKey("OrderJob_$orderId")
        return if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey)
            true
        } else {
            false
        }
    }
}
