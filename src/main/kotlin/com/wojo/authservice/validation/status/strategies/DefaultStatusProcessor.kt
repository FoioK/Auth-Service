package com.wojo.authservice.validation.status.strategies

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.exception.impl.AccountCreateException
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.validation.status.UserStatusStrategy
import org.springframework.stereotype.Component
import java.util.*

@Component
class DefaultStatusProcessor : UserStatusStrategy {

    override fun getStrategyName(): String = UserStatus.DEFAULT.name

    override fun validateStatus(user: UserEntity): Optional<UserEntity> =
            throw AccountCreateException("Error occurred while account was created")

}