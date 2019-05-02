package com.wojo.authservice.service.impl

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.exception.impl.DuplicateEmailException
import com.wojo.authservice.exception.impl.DuplicateNicknameException
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
import java.util.*

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
        checkDuplicates(userInput)
        val user: UserEntity = mapInputToEntity(userInput)
        val savedUser: UserEntity = userRepository.save(user)

        return mapEntityToResponse(savedUser)
    }

    private fun checkDuplicates(userInput: UserInput) {
        val isEmailDuplicate: Boolean = userRepository.findByEmail(userInput.email).isPresent
        val isNicknameDuplicate: Boolean = userRepository.findByNickname(userInput.nickName).isPresent

        if (isEmailDuplicate) {
            throw DuplicateEmailException("Email already exist")
        }

        if (isNicknameDuplicate) {
            throw DuplicateNicknameException("Nickname already exist")
        }
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

    private fun generateUserCode(): String {
        return UUID.randomUUID().toString()
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