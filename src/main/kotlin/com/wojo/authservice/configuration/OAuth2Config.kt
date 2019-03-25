package com.wojo.authservice.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
@ComponentScan(basePackages = ["com.wojo.authservice"])
class OAuth2Config @Autowired
constructor(
        @param:Qualifier("authenticationManagerBean") private val authenticationManager: AuthenticationManager,
        @param:Qualifier("encoder") private val passwordEncoder: PasswordEncoder
) : AuthorizationServerConfigurerAdapter() {

    @Value("\${config.oauth2.clientid}")
    private val clientid: String? = null

    @Value("\${config.oauth2.clientSecret}")
    private val clientSecret: String? = null

    @Value("\${config.oauth2.privateKey}")
    private val privateKey: String? = null

    @Value("\${config.oauth2.publicKey}")
    private val publicKey: String? = null

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient(clientid)
                .secret(passwordEncoder.encode(clientSecret))
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(1800)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer())
    }

    @Bean
    fun tokenStore(): JwtTokenStore {
        return JwtTokenStore(tokenEnhancer())
    }

    @Bean
    fun tokenEnhancer(): JwtAccessTokenConverter {
        val converter = CustomTokenEnhancer()
        converter.setSigningKey(privateKey!!)
        converter.setVerifierKey(publicKey)

        return converter
    }
}