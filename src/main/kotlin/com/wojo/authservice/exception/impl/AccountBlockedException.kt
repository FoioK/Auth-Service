package com.wojo.authservice.exception.impl

import com.wojo.authservice.exception.CustomOAuthException
import com.wojo.authservice.exception.ErrorCode
import org.springframework.http.HttpStatus

class AccountBlockedException(message: String) :
        CustomOAuthException(message, ErrorCode.BLOCKED, HttpStatus.FORBIDDEN)