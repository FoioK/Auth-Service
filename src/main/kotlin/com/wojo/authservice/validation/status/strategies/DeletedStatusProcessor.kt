package com.wojo.authservice.validation.status.strategies

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.exception.impl.AccountDeletedException
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.validation.status.UserStatusStrategy
import org.springframework.stereotype.Component
import java.util.*

@Component
class DeletedStatusProcessor : UserStatusStrategy {

    override fun getStrategyName(): String = UserStatus.DELETED.name

    override fun validateStatus(user: UserEntity): Optional<UserEntity> =
            throw AccountDeletedException("User account was deleted")

}