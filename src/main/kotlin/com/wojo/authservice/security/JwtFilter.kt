package com.wojo.authservice.security

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

        if (SecurityContextHolder.getContext().authentication == null) {
            this.jwtTokenProvider.validateToken(token, request)
            SecurityContextHolder.getContext().authentication = this.jwtTokenProvider.getPrincipal(token)
        }

        chain.doFilter(request, response)
    }

    private fun getAndValidateHeader(request: HttpServletRequest): String =
            if (request.getHeader(HttpHeaders.AUTHORIZATION).startsWith(TOKEN_PREFIX))
                request.getHeader(HttpHeaders.AUTHORIZATION)
            else throw ServletException("Missing or invalid Authorization header")

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !request.requestURI.startsWith("/users")
    }
}