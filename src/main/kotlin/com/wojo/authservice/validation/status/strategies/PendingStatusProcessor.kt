package com.wojo.authservice.validation.status.strategies

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.exception.UserAccountNotActivatedYetException
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.validation.status.UserStatusStrategy
import org.springframework.stereotype.Component
import java.util.*

@Component
class PendingStatusProcessor : UserStatusStrategy {

    override fun getStrategyName() = UserStatus.PENDING.name

    override fun validateStatus(user: UserEntity): Optional<UserEntity> {
        throw UserAccountNotActivatedYetException("Not activated yet")
    }

}