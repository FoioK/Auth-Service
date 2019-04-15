package com.wojo.authservice.service.spec

import com.wojo.authservice.model.UserInput
import org.springframework.stereotype.Service

@Service
interface UserService {

    fun createUser(userInput: UserInput): Long

}