package com.wojo.authservice.service.impl

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.model.CustomUserDetail
import com.wojo.authservice.repository.UserRepository
import com.wojo.authservice.service.spec.PermissionService
import com.wojo.authservice.validation.status.UserStatusEvaluate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService @Autowired constructor(
        private val userRepository: UserRepository,
        private val userStatusEvaluate: UserStatusEvaluate,
        private val permissionService: PermissionService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val users: Set<UserEntity> = userRepository.findByEmailOrNickname(username, username)
        val user: UserEntity = userStatusEvaluate.evaluate(users)
        user.grantedAuthorityList = permissionService.getPermissionsByUserCode(user.code)

        return CustomUserDetail(user)
    }

}