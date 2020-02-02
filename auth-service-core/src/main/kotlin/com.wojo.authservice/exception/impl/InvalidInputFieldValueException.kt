package com.wojo.authservice.exception.impl

import com.wojo.authservice.exception.CustomOAuthException
import com.wojo.authservice.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidInputFieldValueException(message: String, errorCode: ErrorCode) :
        CustomOAuthException(message, errorCode, HttpStatus.BAD_REQUEST)