package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.UserDTO
import com.example.admp_api_flora_mart.entity.User
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class UserMapper(private val modelMapper: ModelMapper): Mapper<UserDTO, User> {

    override fun toDto(entity: User): UserDTO {
        val userDTO = modelMapper.map(entity, UserDTO::class.java)
        return userDTO
    }
    override fun toEntity(dto: UserDTO): User {
        val user = modelMapper.map(dto, User::class.java)
        return user
    }
}