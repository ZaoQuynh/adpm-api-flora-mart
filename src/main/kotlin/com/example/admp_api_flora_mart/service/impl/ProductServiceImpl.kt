package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.dto.ProductDTO
import com.example.admp_api_flora_mart.entity.Product
import com.example.admp_api_flora_mart.mapper.PlantMapper
import com.example.admp_api_flora_mart.mapper.ProductMapper
import com.example.admp_api_flora_mart.reponsitory.ProductRepository
import com.example.admp_api_flora_mart.service.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper,
    private val plantMapper: PlantMapper
): ProductService {
    override fun add(productDTO: ProductDTO): ProductDTO {
        val product = Product(
            plant = productDTO.plant?.let { plantMapper.toEntity(it) },
            price = productDTO.price,
            discount = productDTO.discount,
            stockQty = productDTO.stockQty,
            isDeleted = productDTO.isDeleted
        )

        val savedProduct = productRepository.save(product)
        return productMapper.toDto(savedProduct)
    }

    override fun getProducts(): List<ProductDTO> {
        val products = productRepository.findAll();
        return products.map { productMapper.toDto(it) }
    }

    override fun findTop10SimilarProducts(id: Long): List<ProductDTO> {
        val products = productRepository.findTop10SimilarProducts(id);
        return products.map { productMapper.toDto(it) }
    }

    override fun getProductsByIds(ids: List<Long>): List<ProductDTO> {
        return productRepository.findAllById(ids).map { productMapper.toDto(it) }
    }
}