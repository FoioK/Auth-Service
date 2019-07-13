package com.wojo.authservice.service.impl

import com.wojo.authservice.entity.EXPIRATION_TIME_MINUTES
import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.entity.VerificationToken
import com.wojo.authservice.exception.impl.*
import com.wojo.authservice.model.CustomUserDetail
import com.wojo.authservice.model.UserInput
import com.wojo.authservice.model.UserResponse
import com.wojo.authservice.model.UserStatus
import com.wojo.authservice.repository.UserRepository
import com.wojo.authservice.repository.VerificationRepository
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
import java.time.LocalDateTime
import java.util.*

@Service
class CustomUserDetailService @Autowired constructor(
        private val userRepository: UserRepository,
        private val verificationRepository: VerificationRepository,
        private val userDuplicates: CheckUserDuplicates,
        private val userStatusEvaluate: UserStatusEvaluate,
        private val permissionService: PermissionService,
        private val roleService: RoleService,
        private val emailSender: EmailSenderService
) : UserDetailsService, UserService {

    override fun loadUserByUsername(username: String): UserDetails {
        val users: Set<UserEntity> = userRepository.findByEmailOrUsername(username, username)
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
        updateStatus(savedUser, UserStatus.CREATED)
        verificationProcess(user)

        return mapEntityToResponse(savedUser)
    }

    private fun verificationProcess(user: UserEntity) {
        val token = UUID.randomUUID().toString()
        val verificationToken = VerificationToken(
                token = token,
                user = user,
                createdDate = LocalDateTime.now()
        )
        verificationRepository.save(verificationToken)
        emailSender.sendEmail(user.email, token)
        updateStatus(user, UserStatus.PENDING)
    }

    override fun confirmUserAccount(token: String): Boolean {
        val verificationToken = verificationRepository.findByToken(token)
                .orElseThrow {
                    VerificationTokenNotFoundException("Token ($token) not found")
                }
        if (verificationToken.isActive()) {
            val user = userRepository.findById(verificationToken.user.id)
                    .orElseThrow {
                        AccountNotFoundException("User with id ${verificationToken.user.id} not found")
                    }
            updateStatus(user, UserStatus.ACTIVE)

            return true
        }

        throw TokenExpiredException("Error validating access token: Session has expired on " +
                "${verificationToken.createdDate.plusMinutes(EXPIRATION_TIME_MINUTES)}")
    }

    private fun updateStatus(user: UserEntity, status: UserStatus) {
        user.userStatus = status
        userRepository.saveAndFlush(user)
    }

}