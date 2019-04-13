package com.wojo.authservice.validation.status.strategies

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.exception.impl.AccountModifiedException
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.validation.status.UserStatusStrategy
import org.springframework.stereotype.Component
import java.util.*

@Component
class ModifiedStatusProcessor : UserStatusStrategy {

    override fun getStrategyName(): String = UserStatus.MODIFIED.name

    override fun validateStatus(user: UserEntity): Optional<UserEntity> =
            throw AccountModifiedException("Error occurred while account was modified")
}