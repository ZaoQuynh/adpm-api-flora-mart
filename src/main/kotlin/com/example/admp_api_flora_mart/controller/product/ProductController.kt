package com.example.admp_api_flora_mart.controller.product

import com.example.admp_api_flora_mart.dto.ProductDTO
import com.example.admp_api_flora_mart.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val productService: ProductService) {
    @GetMapping
    fun getProducts(): ResponseEntity<Any>{
        return try {
            val attributes = productService.getProducts();
            ResponseEntity.ok(attributes)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
    @PostMapping
    fun addProduct(@RequestBody product: ProductDTO): ResponseEntity<Any> {
        return try {
            val newProduct = productService.add(product)
            ResponseEntity.ok(newProduct)
        } catch (ex: Exception){
            ResponseEntity.badRequest().body(mapOf("error" to ex.message))
        }
    }
}