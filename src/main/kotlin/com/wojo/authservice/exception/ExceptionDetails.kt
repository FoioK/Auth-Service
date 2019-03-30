package com.wojo.authservice.exception

data class ExceptionDetails(

        val timestamp: Long = 0,
        val status: Int = 403,
        val error: ErrorCode = ErrorCode.UNAUTHORIZED,
        val message: String = "Unauthorized",
        val path: String = ""

)