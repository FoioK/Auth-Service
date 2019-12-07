package com.wojo.authservice.service.util

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.model.UserInput
import com.wojo.authservice.model.UserResponse
import java.time.LocalDateTime
import java.util.*
import kotlin.streams.toList

val mapInputToEntity: (UserInput) -> UserEntity = { userInput ->
    UserEntity(
            code = generateUserCode(),
            email = userInput.email,
            password = userInput.password,
            username = userInput.username,
            createTime = LocalDateTime.now()
    )
}

val mapAllToEntity: (Set<UserInput>) -> List<UserEntity> = {
    it.stream()
            .map { mapInputToEntity(it) }
            .toList()
}

private fun generateUserCode(): String = UUID.randomUUID().toString()

val mapEntityToResponse: (UserEntity, String?) -> UserResponse = { user, token ->
    UserResponse(
            code = user.code,
            email = user.email,
            username = user.username,
            verificationToken = token
    )
}