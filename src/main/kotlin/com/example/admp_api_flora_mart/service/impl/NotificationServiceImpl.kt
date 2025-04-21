package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.NotificationMessage
import com.example.admp_api_flora_mart.entity.ENotificationType
import com.example.admp_api_flora_mart.entity.Notification
import com.example.admp_api_flora_mart.mapper.NotificationMapper
import com.example.admp_api_flora_mart.reponsitory.NotificationRepository
import com.example.admp_api_flora_mart.reponsitory.UserRepository
import com.example.admp_api_flora_mart.service.NotificationService
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationServiceImpl(
    private val notificationRepository: NotificationRepository,
    private val notificationMapper: NotificationMapper,
    private val userRepository: UserRepository,
    private val messagingTemplate: SimpMessagingTemplate
) : NotificationService {

    // Gửi thông báo cho một người dùng
    override fun sendToUser(userId: Long, message: NotificationMessage): NotificationMessage{
        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found with id: $userId") }

        val notification = Notification(
            user = user,
            title = message.title,
            message = message.message,
            date = LocalDateTime.now(),
            type = message.type,
            screen = message.screen,
            params = message.params
        )

        messagingTemplate.convertAndSendToUser(userId.toString(), "/notification", message)
        return notificationMapper.toDto(notificationRepository.save(notification))
    }

    // Gửi thông báo cho tất cả người dùng
    override fun sendToAll(message: NotificationMessage): NotificationMessage {
        val notification = Notification(
            title = message.title,
            message = message.message,
            type = message.type,
            date = LocalDateTime.now(),
            screen = message.screen,
            params = message.params
        )

        // Gửi thông báo đến tất cả người dùng qua WebSocket
        messagingTemplate.convertAndSend("/topic/global-notifications", message)
        return notificationMapper.toDto(notificationRepository.save(notification))
    }

    override fun getByUserId(userId: Long): List<NotificationMessage> {
        val notifications = notificationRepository.findByUserIdOrNull(userId)
        return notifications
            .sortedByDescending { it.date }  // Sắp xếp theo ngày giảm dần
            .map { notification ->
                notificationMapper.toDto(notification)
            }
    }
}
