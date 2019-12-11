package com.wojo.authservice.exception.impl

import com.wojo.authservice.exception.CustomOAuthException
import com.wojo.authservice.exception.ErrorCode
import org.springframework.http.HttpStatus

class AccountNotFoundException(message: String) :
        CustomOAuthException(message, ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND)
