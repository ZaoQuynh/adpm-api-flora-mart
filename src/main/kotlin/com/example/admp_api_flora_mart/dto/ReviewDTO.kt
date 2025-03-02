package com.example.admp_api_flora_mart.dto

import java.time.LocalDateTime

data class ReviewDTO (
    var id: Long?= null,
    var customer: UserDTO?= null,
    var rate: Int?= 0,
    var comment: String?= null,
    var feedback: String?= null,
    var date: LocalDateTime?=null,
)