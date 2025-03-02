package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*

@Entity
@Table(name = "description")
data class Description (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= null,
    var name: String?= null,
    @ManyToOne
    @JoinColumn(name = "description_group_id")
    var descriptionGroup: DescriptionGroup? = null,
    @ManyToMany(mappedBy = "descriptions", fetch = FetchType.LAZY)
    var plants: MutableList<Plant> = mutableListOf()
)