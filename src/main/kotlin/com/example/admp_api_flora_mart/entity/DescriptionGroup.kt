package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*

@Entity
@Table(name = "description_group")
data class DescriptionGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= null,
    var name: String?= null,
    var icon: String?= null,
    @OneToMany(mappedBy = "descriptionGroup", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var descriptions: MutableList<Description> = mutableListOf()
)
