package com.wojo.authservice.configuration

import com.wojo.authservice.entity.VERIFICATION_URI
import com.wojo.authservice.security.JwtFilter
import com.wojo.authservice.security.JwtTokenProvider
import com.wojo.authservice.service.impl.CustomUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration @Autowired constructor(
        private val customUserService: CustomUserService,
        private val jwtTokenProvider: JwtTokenProvider
) : WebSecurityConfigurerAdapter() {

    @Autowired
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customUserService)
                .passwordEncoder(encoder())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(
                        "/h2-console/**",
                        "/users$VERIFICATION_URI",
                        "/favicon.ico").permitAll()
                .antMatchers(HttpMethod.POST, "/users").hasRole("AUTH_CREATE_USER")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(
                        authenticationTokenFilter(),
                        UsernamePasswordAuthenticationFilter::class.java
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)

        http.csrf().disable()
        http.headers().frameOptions().disable()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationTokenFilter(): JwtFilter {
        return JwtFilter(jwtTokenProvider)
    }
}