package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*

@Entity
@Table(name="attribute_group")
data class AttributeGroup (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
    var icon: String? = null,

    @OneToMany(mappedBy = "attributeGroup", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var attributes: MutableList<Attribute> = mutableListOf()
)