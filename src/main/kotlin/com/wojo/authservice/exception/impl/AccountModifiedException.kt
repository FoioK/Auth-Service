package com.wojo.authservice.exception.impl

import com.wojo.authservice.exception.CustomOAuthException
import com.wojo.authservice.exception.ErrorCode
import org.springframework.http.HttpStatus

class AccountModifiedException(message: String) :
        CustomOAuthException(message, ErrorCode.MODIFIED_INCORRECT, HttpStatus.FORBIDDEN)