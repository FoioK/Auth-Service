package com.wojo.authservice.entity

import java.time.LocalDateTime
import javax.persistence.*

private val EXPIRATION: Long = 60 * 24

@Entity
data class VerificationToken(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column
        val token: String = "",

        @OneToOne(targetEntity = UserEntity::class, fetch = FetchType.EAGER)
        @JoinColumn(nullable = false, name = "user_id")
        val user: UserEntity = UserEntity(),

        @Column
        val createdDate: LocalDateTime = LocalDateTime.now()
) {

    fun isTokenActive(): Boolean {
        val currentTime: LocalDateTime = LocalDateTime.now()

        return currentTime.isBefore(createdDate.plusMinutes(EXPIRATION))
    }

}