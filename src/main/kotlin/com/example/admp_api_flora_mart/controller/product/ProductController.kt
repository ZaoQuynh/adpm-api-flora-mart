package com.example.admp_api_flora_mart.controller.product

import com.example.admp_api_flora_mart.dto.ProductDTO
import com.example.admp_api_flora_mart.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val productService: ProductService) {
    @GetMapping
    fun getProducts(): ResponseEntity<Any> {
        return try {
            val products = productService.getProducts();
            ResponseEntity.ok(products)
        } catch (ex: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PostMapping
    fun addProduct(@RequestBody product: ProductDTO): ResponseEntity<Any> {
        return try {
            val newProduct = productService.add(product)
            ResponseEntity.ok(newProduct)
        } catch (ex: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @GetMapping("/similar/{id}")
    fun findTop10SimilarProducts(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            val products = productService.findTop10SimilarProducts(id);
            ResponseEntity.ok(products)
        } catch (ex: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

    @PostMapping("/get-by-ids")
    fun getProductByIds(@RequestBody request: Map<String, List<Long>>): ResponseEntity<Any> {
        return try {
            val ids = request["ids"] ?: emptyList()
            val products = productService.getProductsByIds(ids)
            ResponseEntity.ok(products)
        } catch (ex: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }

}