package com.wojo.authservice.exception

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception

@JsonSerialize(using = CustomOauthExceptionSerializer::class)
open class CustomOAuthException(
        message: String,
        val errorCode: ErrorCode,
        val status: HttpStatus
) : OAuth2Exception(message)
