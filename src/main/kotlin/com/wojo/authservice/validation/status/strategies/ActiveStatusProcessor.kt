package com.wojo.authservice.validation.status.strategies

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.validation.status.UserStatusStrategy
import org.springframework.stereotype.Component
import java.util.*

@Component
class ActiveStatusProcessor : UserStatusStrategy {

    override fun getStrategyName(): String = UserStatus.ACTIVE.name

    override fun validateStatus(user: UserEntity): Optional<UserEntity> =
            Optional.ofNullable(user)
}