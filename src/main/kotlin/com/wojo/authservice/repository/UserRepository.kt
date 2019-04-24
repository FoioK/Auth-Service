package com.wojo.authservice.repository

import com.wojo.authservice.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : PermissionRepository, JpaRepository<UserEntity, Long> {

    fun findByEmailOrNickname(email: String, nickname: String): Set<UserEntity>

    fun findByEmail(email: String): Optional<UserEntity>

    fun findByNickname(nickname: String): Optional<UserEntity>

}