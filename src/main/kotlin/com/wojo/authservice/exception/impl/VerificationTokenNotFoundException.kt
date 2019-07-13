package com.wojo.authservice.exception.impl

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class VerificationTokenNotFoundException(message: String) : RuntimeException(message)