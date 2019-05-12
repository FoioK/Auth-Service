package com.wojo.authservice.service.spec

import org.springframework.stereotype.Service

@Service
interface RoleService {

    fun matchRoleWithUser(userCode: String, roleName: String): Boolean

}