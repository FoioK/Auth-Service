package com.wojo.authservice.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.json.JsonParserFactory.getJsonParser
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.jwt.JwtHelper
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest

const val CLIENT_ID = "client_id"
const val CLIENT_NAME = "user_name"
const val AUTHORITIES_KEY = "authorities"
const val SCOPE_KEY = "scope"

@Configuration
class JwtTokenProvider {

    @Value("\${config.oauth2.clientid}")
    private val clientId: String? = null

    @Value("\${config.oauth2.clientName}")
    private val clientName: String? = null

    fun validateToken(token: String, request: HttpServletRequest) {
        val claims: Map<String, Any> = parseToken(token)

        if (validateClient(claims[CLIENT_ID], claims[CLIENT_NAME]) == null || !validateScope(claims[SCOPE_KEY])) {
            throw ServletException("Invalid token")
        }
    }

    private fun validateClient(clientId: Any?, clientName: Any?) =
            true.takeIf {
                (clientId != null)
                        .and(clientName != null)
                        .and(clientId == this.clientId)
                        .and(clientName == this.clientName)
            }

    private fun validateScope(scope: Any?) =
            if (scope is List<*>)
                scope.find { it == "write" } != null
            else
                false

    fun getPrincipal(token: String): UsernamePasswordAuthenticationToken {
        var authorities: Collection<GrantedAuthority> = mutableListOf()
        val claims: Map<String, Any> = parseToken(token)
        val roles = claims[AUTHORITIES_KEY]
        if (roles is List<*>) {
            roles.forEach { authorities += SimpleGrantedAuthority(it.toString()) }
        }

        return UsernamePasswordAuthenticationToken("", "", authorities)
    }

    private fun parseToken(token: String): Map<String, Any> =
            getJsonParser().parseMap(JwtHelper.decode(token).claims)

}