package com.wojo.authservice.exception.impl

import com.wojo.authservice.exception.CustomOAuthException
import com.wojo.authservice.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class AccountCreateException(message: String) :
        CustomOAuthException(message, ErrorCode.CREATED_INCORRECT, HttpStatus.FORBIDDEN)