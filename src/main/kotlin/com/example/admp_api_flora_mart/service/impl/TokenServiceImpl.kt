package com.example.admp_api_flora_mart.service.impl

import com.example.admp_api_flora_mart.config.JwtProperties
import com.example.admp_api_flora_mart.service.TokenService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.naming.AuthenticationException


@Service
class TokenServiceImpl(
    jwtProperties: JwtProperties
): TokenService {

    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    override fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any>
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()

    override fun extractEmail(token: String): String? {
        return try {
            getAllClaims(token).subject
        } catch (e: Exception) {
            throw AuthenticationException("Invalid token: ${e.message}")
        }
    }

    override fun isExpired(token: String): Boolean =
        getAllClaims(token)
            .expiration
            .before(Date(System.currentTimeMillis()))

    override fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)
            ?: throw AuthenticationException("Token is invalid: email not found.")

        return userDetails.username == email && !isExpired(token)
    }

    override fun validateToken(token: String): Boolean {
        return try {
            getAllClaims(token)
            true
        } catch (e: JwtException) {
            false
        }
    }

    override fun invalidateToken(token: String) {
        val claims = getAllClaims(token)
        val expirationDate = Date(System.currentTimeMillis() - 1000)

        val newToken = Jwts.builder()
            .setClaims(claims)
            .setExpiration(expirationDate)
            .signWith(secretKey)
            .compact()
    }

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .setSigningKey(secretKey)
            .build()

        return parser
            .parseClaimsJws(token)
            .body
    }
}