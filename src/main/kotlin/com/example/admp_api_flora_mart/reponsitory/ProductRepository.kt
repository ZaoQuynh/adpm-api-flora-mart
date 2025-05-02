package com.example.admp_api_flora_mart.reponsitory

import com.example.admp_api_flora_mart.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long> {
    @Query(
        value = """
        SELECT pr.*
        FROM product pr
        JOIN plant_attribute pa ON pa.plant_id = pr.plant_id
        WHERE pa.attribute_id IN (
            SELECT attribute_id FROM plant_attribute WHERE plant_id = (
                SELECT plant_id FROM product WHERE id = :productId
            )
        )
        AND pr.id <> :productId
        AND pr.is_deleted = false
        GROUP BY pr.id
        ORDER BY COUNT(pa.attribute_id) DESC
        LIMIT 10
    """,
        nativeQuery = true
    )
    fun findTop10SimilarProducts(@Param("productId") productId: Long): List<Product>

    fun findAllByIsDeletedFalse(): List<Product>

}