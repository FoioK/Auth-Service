package com.wojo.authservice.repository

import com.wojo.authservice.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String): Optional<Role>

}
