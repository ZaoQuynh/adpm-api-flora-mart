package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*
import javax.annotation.processing.Generated

@Entity
@Table(name="user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= null,
    var fullName: String?= null,
    var email: String?= null,
    var phoneNumber: String?= null,
    var username: String?= null,
    var password: String?= null,
    var tier: EUserTier?= null,
    var points: Int?= null,
    var status: EUserStatus?= null,
    var role: ERole?= null,
    var tokenRefresh: String?= null,
    var avatar: String?= null,
)

enum class ERole {
    CUSTOMER, ADMIN
}

enum class EUserStatus {
    PENDING, ACTIVE, DELETED
}

enum class EUserTier {
    BRONZE, SILVER, GOLD, DIAMOND
}
