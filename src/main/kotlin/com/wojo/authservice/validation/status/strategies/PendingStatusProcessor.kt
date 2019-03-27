package com.wojo.authservice.validation.status.strategies

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.validation.status.UserStatusStrategy
import org.springframework.stereotype.Component
import java.util.*

@Component
class PendingStatusProcessor : UserStatusStrategy {

    override fun getStrategyName() = UserStatus.PENDING.name

    override fun checkStatus(user: UserEntity): Optional<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}