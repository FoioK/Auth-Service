package com.wojo.authservice.exception.impl

import com.wojo.authservice.exception.CustomOAuthException
import com.wojo.authservice.exception.ErrorCode
import org.springframework.http.HttpStatus

class AccountNotActivatedYetException(message: String) :
        CustomOAuthException(message, ErrorCode.NOT_ACTIVATED_YET, HttpStatus.UNAUTHORIZED)