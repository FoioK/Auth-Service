package com.wojo.authservice.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PermissionRepository {

    @Query(value = getPermissionQuery, nativeQuery = true)
    fun getPermissions(@Param("userCode") userCode: Long): List<String>

    companion object {

        const val getPermissionQuery = "SELECT p.name FROM user u " +
                "JOIN user_role ur ON u.code = ur.user_code " +
                "JOIN role r ON ur.role_id = r.id " +
                "JOIN role_permission rp ON r.id = rp.role_id " +
                "JOIN permission p ON rp.permission_id = p.id " +
                "WHERE u.code = :userCode"

    }

}