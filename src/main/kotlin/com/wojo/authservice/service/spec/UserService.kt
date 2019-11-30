package com.wojo.authservice.service.spec

import com.wojo.authservice.model.UserInput
import com.wojo.authservice.model.UserResponse
import org.springframework.stereotype.Service

@Service
interface UserService {

    fun createUser(userInput: UserInput, isNeedVerification: Boolean): UserResponse

    fun confirmUserAccount(token: String): Boolean

}