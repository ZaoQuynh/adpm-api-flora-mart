package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "`order`")
data class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    var customer: User?= null,
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var orderItems: MutableList<OrderItem> = mutableListOf(),
    var status: EOrderStatus?= null,
    var createDate: LocalDateTime?= null,

    @ManyToMany
    @JoinTable(
        name = "order_voucher",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "voucher_id")]
    )
    var vouchers: MutableList<Voucher> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = true)
    var payment: Payment?= null,
    var address: String?= null
)

enum class EOrderStatus{
    NEW, CONFIRMED, PREPARING, SHIPPING, SHIPPED, DELIVERED, CANCELED
}