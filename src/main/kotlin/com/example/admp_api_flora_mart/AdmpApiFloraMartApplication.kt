package com.example.admp_api_flora_mart

import com.example.admp_api_flora_mart.config.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class)
class AdmpApiFloraMartApplication

fun main(args: Array<String>) {
	runApplication<AdmpApiFloraMartApplication>(*args)
}
