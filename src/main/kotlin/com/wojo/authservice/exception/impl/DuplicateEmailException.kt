package com.wojo.authservice.exception.impl

import com.wojo.authservice.exception.CustomOAuthException
import com.wojo.authservice.exception.ErrorCode
import org.springframework.http.HttpStatus

class DuplicateEmailException(message: String) :
        CustomOAuthException(message, ErrorCode.DUPLICATE_EMAIL, HttpStatus.CONFLICT)