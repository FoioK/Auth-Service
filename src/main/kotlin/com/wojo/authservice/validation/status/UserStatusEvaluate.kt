package com.wojo.authservice.validation.status

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.exception.impl.AccountNotFoundException
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.validation.status.strategies.DefaultStatusProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserStatusEvaluate @Autowired constructor(
        private val userStatusStrategies: List<UserStatusStrategy>
) {

    fun evaluate(users: Set<UserEntity>): UserEntity {
        val user = filterUsersByStatus(users)

        return evaluateStatusByStrategy(user)
    }

    private fun filterUsersByStatus(users: Set<UserEntity>): UserEntity {
        if (users.isEmpty()) {
            throw AccountNotFoundException("User not found")
        }

        return users.asSequence().find { it.userStatus == UserStatus.ACTIVE }
                ?: users.asSequence().find { it.userStatus == UserStatus.MODIFIED }
                ?: users.first()
    }

    private fun evaluateStatusByStrategy(user: UserEntity): UserEntity =
            getStrategy(user.userStatus).validateStatus(user)
                    .orElseThrow {
                        AccountNotFoundException("Error occurred when server validate user status")
                    }

    private fun getStrategy(userStatus: UserStatus): UserStatusStrategy =
            userStatusStrategies.stream()
                    .filter { status -> status.getStrategyName() == userStatus.name }
                    .findFirst()
                    .orElseGet { DefaultStatusProcessor() }
}