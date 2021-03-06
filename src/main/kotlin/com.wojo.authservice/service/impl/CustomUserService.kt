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
import com.wojo.authservice.service.util.*
import com.wojo.authservice.validation.input.UserInputValidator
import com.wojo.authservice.validation.status.UserStatusEvaluate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*

@Service
class CustomUserService @Autowired constructor(
        private val userRepository: UserRepository,
        private val verificationRepository: VerificationRepository,
        private val userInputValidator: UserInputValidator,
        private val userStatusEvaluate: UserStatusEvaluate,
        private val permissionService: PermissionService,
        private val roleService: RoleService
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
        DuplicateUsernameException::class
    ])
    override fun createUser(userInput: UserInput, isNeedVerification: Boolean): UserResponse {
        userInputValidator.checkDuplicates(userInput)
        val user: UserEntity = mapInputToEntity(userInput)
        val savedUser: UserEntity = userRepository.save(user)
        if (savedUser.id == 0L || !roleService.matchRoleWithUser(savedUser.code, "user")) {
            throw CreateEntityException("Account was not created")
        }
        updateStatus(savedUser, UserStatus.CREATED)
        val verificationToken = if (isNeedVerification) verificationProcess(user) else updateWithoutVerification(user)

        return mapEntityToResponse(savedUser, verificationToken)
    }

    private fun verificationProcess(user: UserEntity): String? =
            run {
                val token = UUID.randomUUID().toString()
                val verificationToken = VerificationToken(
                        token = token,
                        user = user,
                        createdDate = LocalDateTime.now()
                )
                verificationRepository.save(verificationToken)
                updateStatus(user, UserStatus.PENDING)

                token
            }

    private fun updateWithoutVerification(user: UserEntity): String? =
            run {
                updateStatus(user, UserStatus.ACTIVE)

                null
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

    @Transactional(rollbackFor = [
        CreateEntityException::class,
        DuplicateEmailException::class,
        DuplicateUsernameException::class
    ])
    override fun importFromFile(file: MultipartFile): List<UserResponse> {
        val users: Set<UserInput> = CsvUtils.read(UserInput::class.java, file.inputStream)
        userInputValidator.validateAll(users)
        userInputValidator.checkDuplicatesForAll(users)

        val entities: List<UserEntity> = mapAllToEntity(users)
        val savedUsers: List<UserEntity> = userRepository.saveAll(entities)
        savedUsers.forEach { user ->
            roleService.matchRoleWithUser(user.code, "user")
            updateStatus(user, UserStatus.ACTIVE)
        }

        return mapAllToResponse(savedUsers)
    }

}