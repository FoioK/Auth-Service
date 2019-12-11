package com.wojo.authservice.model

data class UserResponse(

        val code: String,
        val email: String,
        val username: String,
        val verificationToken: String?

)