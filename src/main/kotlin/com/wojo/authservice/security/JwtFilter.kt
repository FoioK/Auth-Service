package com.wojo.authservice.security

import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter : OncePerRequestFilter() {

    override fun doFilterInternal(p0: HttpServletRequest, p1: HttpServletResponse, p2: FilterChain) {
        TODO("not implemented")
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !request.requestURI.startsWith("/users")
    }
}