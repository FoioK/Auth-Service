package com.wojo.authservice.repository

import com.wojo.authservice.entity.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VerificationRepository : JpaRepository<VerificationToken, Long> {

    fun findByToken(token: String): Optional<VerificationToken>

}