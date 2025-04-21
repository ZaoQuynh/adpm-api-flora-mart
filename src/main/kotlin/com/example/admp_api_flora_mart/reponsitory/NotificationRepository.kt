package com.example.admp_api_flora_mart.reponsitory

import com.example.admp_api_flora_mart.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findByUserId(userId: Long): List<Notification>
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId OR n.user IS NULL")
    fun findByUserIdOrNull(@Param("userId") userId: Long): List<Notification>
}