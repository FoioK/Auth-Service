package com.wojo.authservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UserAccountNotActivatedYetException(message: String) :
        CustomOAuthException(message, ErrorCode.NOT_ACTIVATED_YET)