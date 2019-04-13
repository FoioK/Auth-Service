package com.wojo.authservice.service.spec

import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service

@Service
interface PermissionService {

    fun getPermissionsByUserCode(code: Long): Collection<GrantedAuthority>

}