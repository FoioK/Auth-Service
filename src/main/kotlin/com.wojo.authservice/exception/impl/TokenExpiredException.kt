package com.wojo.authservice.exception.impl

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class TokenExpiredException(message: String) : RuntimeException(message)