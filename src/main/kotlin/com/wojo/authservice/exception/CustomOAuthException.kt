package com.wojo.authservice.exception

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception

@JsonSerialize(using = CustomOauthExceptionSerializer::class)
open class CustomOAuthException(
        message: String,
        val errorCode: ErrorCode
) : OAuth2Exception(message)
