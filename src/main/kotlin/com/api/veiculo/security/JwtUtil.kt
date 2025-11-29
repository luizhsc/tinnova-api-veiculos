package com.api.veiculo.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*

object JwtUtil {
    private val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(username: String, roles: List<String>): String =
        Jwts.builder()
            .setSubject(username)
            .claim("roles", roles)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
            .signWith(key)
            .compact()

    fun validateToken(token: String): String? =
        try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
            claims.body.subject
        } catch (e: Exception) {
            null
        }
}