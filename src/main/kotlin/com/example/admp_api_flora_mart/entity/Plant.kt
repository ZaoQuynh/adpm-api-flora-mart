package com.example.admp_api_flora_mart.entity

import jakarta.persistence.*

@Entity
@Table(name = "plant")
data class Plant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= null,
    var name: String?= null,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "plant_description",
        joinColumns = [JoinColumn(name = "plant_id")],
        inverseJoinColumns = [JoinColumn(name = "description_id")]
    )
    var descriptions: MutableList<Description> = mutableListOf(),

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "plant_attribute",
        joinColumns = [JoinColumn(name = "plant_id")],
        inverseJoinColumns = [JoinColumn(name = "attribute_id")]
    )
    var attributes: MutableList<Attribute> = mutableListOf(),

    var img: String?= null,
)