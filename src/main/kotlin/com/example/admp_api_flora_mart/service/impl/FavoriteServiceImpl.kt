package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.ProductDTO
import com.example.admp_api_flora_mart.mapper.ProductMapper
import com.example.admp_api_flora_mart.mapper.UserMapper
import com.example.admp_api_flora_mart.reponsitory.ProductRepository
import com.example.admp_api_flora_mart.reponsitory.UserRepository
import com.example.admp_api_flora_mart.service.FavoriteService
import org.springframework.stereotype.Service

@Service
class FavoriteServiceImpl(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
): FavoriteService{

    override fun getFavoriteProducts(userId: Long): List<ProductDTO> {
        return try {
            val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }
            user.favoriteProducts.map { productMapper.toDto(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun addFavoriteProduct(userId: Long, productId: Long) {
        try {
            val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }
            val product = productRepository.findById(productId).orElseThrow { Exception("Product not found") }

            if (!user.favoriteProducts.contains(product)) {
                user.favoriteProducts.add(product)
                userRepository.save(user)
            }
        } catch (e: Exception) {
            throw RuntimeException("Error adding favorite product: ${e.message}")
        }
    }

    override fun removeFavoriteProduct(userId: Long, productId: Long) {
        try {
            val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }
            val product = productRepository.findById(productId).orElseThrow { Exception("Product not found") }

            if (user.favoriteProducts.contains(product)) {
                user.favoriteProducts.remove(product)
                userRepository.save(user)
            }
        } catch (e: Exception) {
            throw RuntimeException("Error removing favorite product: ${e.message}")
        }
    }

    override fun checkFavorite(userId: Long, productId: Long): Boolean {
        return try {
            val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }
            return user.favoriteProducts.any { it.id == productId }
        } catch (e: Exception) {
            throw RuntimeException("Error check favorite product: ${e.message}")
        }
    }
}