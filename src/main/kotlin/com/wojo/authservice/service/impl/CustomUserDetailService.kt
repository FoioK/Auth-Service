package com.wojo.authservice.service.impl

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.model.CustomUserDetail
import com.wojo.authservice.model.UserInput
import com.wojo.authservice.model.UserResponse
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.repository.UserRepository
import com.wojo.authservice.service.spec.PermissionService
import com.wojo.authservice.service.spec.UserService
import com.wojo.authservice.validation.status.UserStatusEvaluate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CustomUserDetailService @Autowired constructor(
        private val userRepository: UserRepository,
        private val userStatusEvaluate: UserStatusEvaluate,
        private val permissionService: PermissionService
) : UserDetailsService, UserService {

    override fun loadUserByUsername(username: String): UserDetails {
        val users: Set<UserEntity> = userRepository.findByEmailOrNickname(username, username)
        val user: UserEntity = userStatusEvaluate.evaluate(users)
        user.grantedAuthorityList = permissionService.getPermissionsByUserCode(user.code)

        return CustomUserDetail(user)
    }

    override fun createUser(userInput: UserInput): UserResponse {
        // TODO duplicate check
        val user: UserEntity = mapInputToEntity(userInput)
        val savedUser: UserEntity = userRepository.save(user)

        return mapEntityToResponse(savedUser)
    }

    private val mapInputToEntity: (UserInput) -> UserEntity = { userInput ->
        UserEntity.build {
            code = generateUserCode()
            email = userInput.email
            password = userInput.password
            userStatus = UserStatus.CREATED
            nickname = userInput.nickName
            createTime = LocalDateTime.now()
        }
    }

    private fun generateUserCode(): Long {
        return 10L
    }

    private val mapEntityToResponse: (UserEntity) -> UserResponse = { user ->
        UserResponse.build {
            id = user.id
            code = user.code
            email = user.email
            nickName = user.nickname
        }
    }

}