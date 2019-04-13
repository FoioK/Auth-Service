package com.wojo.authservice.validation.status.strategies

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.exception.impl.AccountBlockedException
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.validation.status.UserStatusStrategy
import org.springframework.stereotype.Component
import java.util.*

@Component
class BlockedStatusProcessor : UserStatusStrategy {

    override fun getStrategyName(): String = UserStatus.BLOCKED.name

    override fun validateStatus(user: UserEntity): Optional<UserEntity> =
            throw AccountBlockedException("User account was blocked")
}