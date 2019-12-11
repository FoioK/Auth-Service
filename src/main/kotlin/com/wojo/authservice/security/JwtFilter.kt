package com.wojo.authservice.security

import com.wojo.authservice.entity.VERIFICATION_URI
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val TOKEN_PREFIX = "Bearer "

class JwtFilter(
        private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain) {
        val header: String = getAndValidateHeader(request)
        val token: String = header.replace(TOKEN_PREFIX, "")
        this.jwtTokenProvider.validateToken(token, request)
        SecurityContextHolder.getContext().authentication = this.jwtTokenProvider.getPrincipal(token)

        chain.doFilter(request, response)
    }

    private fun getAndValidateHeader(request: HttpServletRequest): String {
        val authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith(TOKEN_PREFIX)) {
            return authorizationHeaderValue
        } else {
            throw ServletException("Missing or invalid Authorization header")
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean = "/users" != request.requestURI
}