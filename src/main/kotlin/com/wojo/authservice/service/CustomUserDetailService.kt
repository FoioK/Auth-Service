package com.wojo.authservice.service

import com.wojo.authservice.entity.UserEntity
import com.wojo.authservice.model.CustomUserDetail
import com.wojo.authservice.repository.UserRepository
import com.wojo.authservice.validation.status.UserStatusEvaluate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.persistence.EntityNotFoundException

@Service
class CustomUserDetailService @Autowired constructor(
        private val userRepository: UserRepository,
        private val userStatusEvaluate: UserStatusEvaluate
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val users: List<UserEntity> = userRepository.findByEmailOrNickname(username, username)

        if (users.isEmpty()) {
            throw EntityNotFoundException("User not found")
        }
        val user: UserEntity = userStatusEvaluate.evaluateStatus(users[0])
        val permissions: Collection<GrantedAuthority> =
                userRepository.getPermissions(user.code)
                        .stream()
                        .map { SimpleGrantedAuthority(it) }
                        .collect(Collectors.toList())
        user.grantedAuthorityList = permissions

        return CustomUserDetail(user)
    }
}