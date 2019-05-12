package com.wojo.authservice.service.util

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.model.UserInput
import com.wojo.authservice.model.UserResponse
import com.wojo.authservice.model.UserStatus
import java.time.LocalDateTime
import java.util.*

val mapInputToEntity: (UserInput) -> UserEntity = { userInput ->
    UserEntity.build {
        code = generateUserCode()
        email = userInput.email
        password = userInput.password
        userStatus = UserStatus.CREATED
        nickname = userInput.nickName
        createTime = LocalDateTime.now()
    }
}

private fun generateUserCode(): String {
    return UUID.randomUUID().toString()
}

val mapEntityToResponse: (UserEntity) -> UserResponse = { user ->
    UserResponse.build {
        id = user.id
        code = user.code
        email = user.email
        nickName = user.nickname
    }
}