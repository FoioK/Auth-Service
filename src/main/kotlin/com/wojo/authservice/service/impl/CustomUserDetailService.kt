package com.wojo.authservice.service.impl

import com.wojo.authservice.entity.Role
import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.entity.UserRole
import com.wojo.authservice.exception.impl.CreateEntityException
import com.wojo.authservice.exception.impl.DuplicateEmailException
import com.wojo.authservice.exception.impl.DuplicateNicknameException
import com.wojo.authservice.model.CustomUserDetail
import com.wojo.authservice.model.UserInput
import com.wojo.authservice.model.UserResponse
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.repository.RoleRepository
import com.wojo.authservice.repository.UserRepository
import com.wojo.authservice.repository.UserRoleRepository
import com.wojo.authservice.service.spec.PermissionService
import com.wojo.authservice.service.spec.UserService
import com.wojo.authservice.validation.status.UserStatusEvaluate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class CustomUserDetailService @Autowired constructor(
        private val userRepository: UserRepository,
        private val userRoleRepository: UserRoleRepository,
        private val roleRepository: RoleRepository,
        private val userStatusEvaluate: UserStatusEvaluate,
        private val permissionService: PermissionService
) : UserDetailsService, UserService {

    override fun loadUserByUsername(username: String): UserDetails {
        val users: Set<UserEntity> = userRepository.findByEmailOrNickname(username, username)
        val user: UserEntity = userStatusEvaluate.evaluate(users)
        user.grantedAuthorityList = permissionService.getPermissionsByUserCode(user.code)

        return CustomUserDetail(user)
    }

    @Transactional(rollbackFor = [
        CreateEntityException::class,
        DuplicateEmailException::class,
        DuplicateNicknameException::class
    ])
    override fun createUser(userInput: UserInput): UserResponse {
        checkDuplicates(userInput)
        val user: UserEntity = mapInputToEntity(userInput)
        val savedUser: UserEntity = userRepository.save(user)
        if (savedUser.id == 0L || !insertRole(savedUser.code, "user")) {
            throw CreateEntityException("Account was not created")
        }

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

    private fun insertRole(userCode: String, roleName: String): Boolean {
        val role: Optional<Role> = roleRepository.findByName(roleName)

        if (role.isPresent) {
            return insertUserRole(userCode, role.get())
        }

        return false
    }

    private fun insertUserRole(code: String, role: Role): Boolean {
        val userRole: UserRole = UserRole.build {
            userCode = code
            this.role = role
        }

        return userRoleRepository.save(userRole).id != 0L
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