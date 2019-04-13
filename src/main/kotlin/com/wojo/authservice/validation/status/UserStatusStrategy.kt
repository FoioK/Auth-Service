package com.wojo.authservice.validation.status

import com.wojo.authservice.entity.UserEntity
import java.util.*

interface UserStatusStrategy {

    fun getStrategyName(): String

    fun validateStatus(user: UserEntity): Optional<UserEntity>

}