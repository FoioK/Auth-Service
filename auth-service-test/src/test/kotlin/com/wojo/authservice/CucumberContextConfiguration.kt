package com.wojo.authservice;

import org.junit.Before;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {TestConfig.class})
@EnableConfigurationProperties
@SpringBootTest
@ComponentScan
public class CucumberContextConfiguration {

    @Before
    public void setup_cucumber_spring_context() {

    }

}
