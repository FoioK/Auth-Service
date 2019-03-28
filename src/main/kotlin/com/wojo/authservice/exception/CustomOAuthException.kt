package com.wojo.authservice.exception

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
open class CustomOAuthException(
        message: String,
        val errorCode: ErrorCode
) : OAuth2Exception(message)
