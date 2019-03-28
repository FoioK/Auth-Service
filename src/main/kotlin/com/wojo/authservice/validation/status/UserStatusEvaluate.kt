package com.wojo.authservice.validation.status

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.exception.UserAccountNotFoundException
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.validation.status.strategies.DefaultStatusProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserStatusEvaluate @Autowired constructor(
        private val userStatusStrategies: List<UserStatusStrategy>
) {

    fun evaluateStatus(user: UserEntity): UserEntity =
            getStrategy(user.userStatus).validateStatus(user)
                    .orElseThrow {
                        UserAccountNotFoundException("Error occurred when server validate user status", "X-CODE")
                    }

    private fun getStrategy(userStatus: UserStatus): UserStatusStrategy =
            userStatusStrategies.stream()
                    .filter { status -> status.getStrategyName() == userStatus.name }
                    .findFirst()
                    .orElseGet { DefaultStatusProcessor() }
}