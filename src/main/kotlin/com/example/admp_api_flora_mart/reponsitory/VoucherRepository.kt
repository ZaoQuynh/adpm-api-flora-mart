package com.example.admp_api_flora_mart.reponsitory

import com.example.admp_api_flora_mart.entity.Voucher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VoucherRepository : JpaRepository<Voucher, Long> {
    @Query("""
    SELECT v.*
    FROM `voucher` v
    WHERE v.id NOT IN (
        SELECT ov.voucher_id
        FROM `order_voucher` ov
        JOIN `order` o ON o.id = ov.order_id
        WHERE o.customer_id = :userId
    )
    -- AND v.create_date < CURRENT_TIMESTAMP
    AND v.end_date >= CURRENT_TIMESTAMP
    AND EXISTS (
        SELECT 1
        FROM `user` u
        JOIN `voucher_type` vt ON vt.id = v.voucher_type_id
        WHERE u.id = :userId
        AND u.tier >= CAST(vt.type AS UNSIGNED)
    )
""", nativeQuery = true)
    fun findUsedVouchersByUser(@Param("userId") userId: Long): List<Voucher>
}
