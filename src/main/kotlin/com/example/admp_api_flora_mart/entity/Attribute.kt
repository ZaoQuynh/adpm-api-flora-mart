package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*

@Entity
@Table(name = "attribute")
data class Attribute(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
    var icon: String? = null,
    @ManyToOne
    @JoinColumn(name = "attribute_group_id")
    var attributeGroup: AttributeGroup? = null,
    @ManyToMany(mappedBy = "attributes", fetch = FetchType.LAZY)
    var plants: MutableList<Plant> = mutableListOf()
)