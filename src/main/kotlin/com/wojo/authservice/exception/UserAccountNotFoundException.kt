package com.wojo.authservice.exception

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserAccountNotFoundException(message: String, codeX: String) :
        CustomOAuthException(message, ErrorCode.NOT_FOUND)
