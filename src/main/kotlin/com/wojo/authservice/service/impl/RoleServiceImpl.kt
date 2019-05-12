package com.wojo.authservice.service.impl

import com.wojo.authservice.entity.Role
import com.wojo.authservice.entity.UserRole
import com.wojo.authservice.repository.RoleRepository
import com.wojo.authservice.repository.UserRoleRepository
import com.wojo.authservice.service.spec.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoleServiceImpl @Autowired constructor(
        private val roleRepository: RoleRepository,
        private val userRoleRepository: UserRoleRepository
) : RoleService {

    override fun matchRoleWithUser(userCode: String, roleName: String): Boolean {
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
}