package com.example.admp_api_flora_mart.controller.user.request

data class UpdateUserInfoRequest(
    var id: Long?= null,
    var fullName: String?= null,
    var username: String?= null,
    var phoneNumber: String?= null,
    var avatar: String?= null
)
