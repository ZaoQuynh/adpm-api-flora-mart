package com.example.admp_api_flora_mart.mapper

import com.example.admp_api_flora_mart.dto.ReviewDTO
import com.example.admp_api_flora_mart.entity.Review
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class ReviewMapper(private val modelMapper: ModelMapper): Mapper<ReviewDTO, Review> {
    override fun toDto(entity: Review): ReviewDTO {
        return modelMapper.map(entity, ReviewDTO::class.java)
    }

    override fun toEntity(dto: ReviewDTO): Review {
        return modelMapper.map(dto, Review::class.java)
    }
}