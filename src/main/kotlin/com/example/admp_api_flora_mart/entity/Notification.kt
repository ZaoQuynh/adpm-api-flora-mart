package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notification")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var type: ENotificationType?= null,
    var title: String? =null,
    var message: String? =null,

    @Column(name = "screen_name")
    var screen: String? = null,

    var date: LocalDateTime?= LocalDateTime.now(),

    @ElementCollection(fetch = FetchType.LAZY) // Add this annotation
    @MapKeyColumn(name = "param_key") // This specifies the column for keys
    @Column(name = "param_value") // This specifies the column for values
    @CollectionTable(name = "notification_params", joinColumns = [JoinColumn(name = "notification_id")]) // This specifies the table that will hold the map
    var params: Map<String, String>? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
)

enum class ENotificationType {
    ORDER, POST, EVENT, REVIEW, COMMENT
}