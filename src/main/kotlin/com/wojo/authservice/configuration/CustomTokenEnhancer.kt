package com.wojo.authservice.configuration

import com.wojo.authservice.model.CustomUserDetail
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import java.util.*

class CustomTokenEnhancer : JwtAccessTokenConverter() {

    override fun enhance(
            accessToken: OAuth2AccessToken,
            authentication: OAuth2Authentication): OAuth2AccessToken {

        val user = authentication.principal as CustomUserDetail
        val info = LinkedHashMap(accessToken.additionalInformation)

        info["code"] = user.code
        info["email"] = user.email
        info["createTime"] = user.createTime.toString()

        val customAccessToken = DefaultOAuth2AccessToken(accessToken)
        customAccessToken.additionalInformation = info

        return super.enhance(customAccessToken, authentication)
    }
}