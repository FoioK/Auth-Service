package com.wojo.authservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer

@SpringBootApplication
@EnableAuthorizationServer
class AuthServiceApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<AuthServiceApplication>(*args)
        }
    }
}
