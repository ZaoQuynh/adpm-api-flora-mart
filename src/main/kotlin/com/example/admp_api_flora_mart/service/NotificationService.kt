package com.example.admp_api_flora_mart.service

import com.example.admp_api_flora_mart.dto.NotificationMessage

interface NotificationService {
    fun sendToAll(message: NotificationMessage): NotificationMessage
    fun sendToUser(userId: Long, message: NotificationMessage): NotificationMessage
    fun getByUserId(userId: Long): List<NotificationMessage>
}