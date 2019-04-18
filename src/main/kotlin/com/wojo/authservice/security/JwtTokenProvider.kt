package com.wojo.authservice.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import javax.servlet.http.HttpServletRequest

@Configuration
class JwtTokenProvider {

    @Value("\${config.oauth2.clientSecret}")
    private val secretKey: String? = null

    fun validateToken(token: String, request: HttpServletRequest) {

    }

    fun authorizeToken(token: String): UsernamePasswordAuthenticationToken {

    }

    private fun parseToken(token: String): Claims =
            Jwts.parser()
                    .setSigningKey(this.secretKey)
                    .parseClaimsJws(token)
                    .body
}