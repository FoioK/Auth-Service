package com.wojo.authservice

import org.junit.Before
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfig::class])
@EnableConfigurationProperties
@SpringBootTest
@ComponentScan
class CucumberContextConfiguration {

    @Before
    fun setup_cucumber_spring_context() {
    }

}