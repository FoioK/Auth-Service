package com.wojo.authservice.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.net.InetAddress
import java.net.UnknownHostException

@Service
class EnvService @Autowired constructor(
        private val environment: Environment
) {

    @Throws(UnknownHostException::class)
    fun getHostName() = InetAddress.getLocalHost().getHostAddress()

    fun getPort() = environment.getProperty("local.server.port")

    @Throws(UnknownHostException::class)
    fun getServerUrlPrefix() = "http://" + getHostName() + ":" + getPort()

}