package com.wojo.authservice.service.impl

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.exception.impl.CreateEntityException
import com.wojo.authservice.exception.impl.DuplicateEmailException
import com.wojo.authservice.exception.impl.DuplicateNicknameException
import com.wojo.authservice.model.CustomUserDetail
import com.wojo.authservice.model.UserInput
import com.wojo.authservice.model.UserResponse
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.repository.UserRepository
import com.wojo.authservice.service.spec.PermissionService
import com.wojo.authservice.service.spec.RoleService
import com.wojo.authservice.service.spec.UserService
import com.wojo.authservice.service.util.mapEntityToResponse
import com.wojo.authservice.service.util.mapInputToEntity
import com.wojo.authservice.validation.input.CheckUserDuplicates
import com.wojo.authservice.validation.status.UserStatusEvaluate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailService @Autowired constructor(
        private val userRepository: UserRepository,
        private val userDuplicates: CheckUserDuplicates,
        private val userStatusEvaluate: UserStatusEvaluate,
        private val permissionService: PermissionService,
        private val roleService: RoleService
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
        userDuplicates.checkDuplicates(userInput)
        val user: UserEntity = mapInputToEntity(userInput)
        val savedUser: UserEntity = userRepository.save(user)
        if (savedUser.id == 0L || !roleService.matchRoleWithUser(savedUser.code, "user")) {
            throw CreateEntityException("Account was not created")
        }
        savedUser.userStatus = UserStatus.ACTIVE

        return mapEntityToResponse(savedUser)
    }

}