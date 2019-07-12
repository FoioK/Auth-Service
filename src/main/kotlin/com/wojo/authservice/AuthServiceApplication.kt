package com.wojo.authservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer

@SpringBootApplication
@EnableAuthorizationServer
@EnableAsync
class AuthServiceApplication

fun main(args: Array<String>) {
    runApplication<AuthServiceApplication>(*args)
}
