package com.wojo.authservice.exception.impl

import com.wojo.authservice.exception.CustomOAuthException
import com.wojo.authservice.exception.ErrorCode
import org.springframework.http.HttpStatus

class AccountDeletedException(message: String) :
        CustomOAuthException(message, ErrorCode.DELETED, HttpStatus.FORBIDDEN)