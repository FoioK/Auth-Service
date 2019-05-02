package com.wojo.authservice.service.impl

import com.wojo.authservice.repository.UserRepository
import com.wojo.authservice.service.spec.PermissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class PermissionServiceImpl @Autowired constructor(
        private val userRepository: UserRepository
) : PermissionService {

    override fun getPermissionsByUserCode(code: String): Collection<GrantedAuthority> =
            userRepository.getPermissions(code)
                    .stream()
                    .map { SimpleGrantedAuthority(it) }
                    .collect(Collectors.toList())
}