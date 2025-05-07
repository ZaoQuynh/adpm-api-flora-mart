package com.example.admp_api_flora_mart.controller.favorite

import com.example.admp_api_flora_mart.service.FavoriteService
import com.example.admp_api_flora_mart.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/favorite")
class FavoriteController(
    private val favoriteService: FavoriteService,
    private val userService: UserService
) {
    @GetMapping("/{userId}")
    fun getFavorites(@PathVariable userId: Long): ResponseEntity<Any> {
        return try {
            val products = favoriteService.getFavoriteProducts(userId)
            ResponseEntity.ok(products)
        } catch (ex: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @GetMapping("/{userId}/{productId}")
    fun checkFavorite(@PathVariable userId: Long, @PathVariable productId: Long): ResponseEntity<Any> {
        return try {
            val isFavorite = favoriteService.checkFavorite(userId, productId)

            ResponseEntity.ok(isFavorite)
        } catch (ex: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PostMapping("/{userId}/{productId}")
    fun addFavorite(@PathVariable userId: Long, @PathVariable productId: Long): ResponseEntity<Any> {
        return try {
            favoriteService.addFavoriteProduct(userId, productId)
            ResponseEntity.ok("Product added to favorites")
        } catch (ex: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @DeleteMapping("/{userId}/{productId}")
    fun removeFavorite(@PathVariable userId: Long, @PathVariable productId: Long): ResponseEntity<Any> {
        return try {
            favoriteService.removeFavoriteProduct(userId, productId)
            ResponseEntity.ok("Product removed from favorites")
        } catch (ex: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}