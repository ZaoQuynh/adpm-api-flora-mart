package com.example.admp_api_flora_mart.dto

import com.example.admp_api_flora_mart.entity.ENotificationType
import java.time.LocalDateTime

data class NotificationMessage(
    var type: ENotificationType? =null,
    var title: String? =null,
    var user: UserDTO? =null,
    var message: String? =null,
    var date: LocalDateTime?= LocalDateTime.now(),
    var screen: String? = null,
    var params: Map<String, String>? = null
)
