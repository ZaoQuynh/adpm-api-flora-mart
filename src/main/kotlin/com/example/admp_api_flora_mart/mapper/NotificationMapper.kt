package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.NotificationMessage
import com.example.admp_api_flora_mart.entity.Notification
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class NotificationMapper(private val modelMapper: ModelMapper) {

    // Chuyển entity Notification thành DTO NotificationMessage
    fun toDto(entity: Notification): NotificationMessage {
        return modelMapper.map(entity, NotificationMessage::class.java)
    }

    // Chuyển DTO NotificationMessage thành entity Notification
    fun toEntity(dto: NotificationMessage): Notification {
        return modelMapper.map(dto, Notification::class.java)
    }
}