package com.wojo.authservice.service

import com.wojo.authservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class PermissionService @Autowired constructor(
        private val userRepository: UserRepository
) {

    fun getPermissionsByUserCode(code: Long): Collection<GrantedAuthority> =
            userRepository.getPermissions(code)
                    .stream()
                    .map { SimpleGrantedAuthority(it) }
                    .collect(Collectors.toList())
}