package com.example.admp_api_flora_mart.controller.notification

import com.example.admp_api_flora_mart.dto.NotificationMessage
import com.example.admp_api_flora_mart.service.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/notification")
class NotificationController(
    private val notificationService: NotificationService
) {

    @GetMapping("/user/{userId}")
    fun getNotificationsByUserId(@PathVariable userId: Long): ResponseEntity<Any> {
        return try {
            val notifications = notificationService.getByUserId(userId)
            ResponseEntity.ok(notifications)
        } catch (ex: Exception) {
            ex.printStackTrace();
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PostMapping("/user/{userId}")
    fun notifyUser(@PathVariable userId: Long, @RequestBody message: NotificationMessage): ResponseEntity<Any> {
        return try {
            val savedNotification = notificationService.sendToUser(userId, message)
            ResponseEntity.ok(savedNotification)
        } catch (ex: Exception) {
            ex.printStackTrace();
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PostMapping("/all")
    fun notifyAll(@RequestBody message: NotificationMessage): ResponseEntity<Any> {
        return try {
            val savedNotification = notificationService.sendToAll(message)
            return ResponseEntity.ok(savedNotification)
        } catch (ex: Exception) {
            ex.printStackTrace();
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}