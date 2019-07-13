package com.wojo.authservice.entity

import java.time.LocalDateTime
import javax.persistence.*

private val EXPIRATION_TIME_MINUTES: Long = 60 * 24
const val VERIFICATION_URI: String = "/confirm-account"
const val VERIFICATION_PARAM_NAME: String = "token"

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

        return currentTime.isBefore(createdDate.plusMinutes(EXPIRATION_TIME_MINUTES))
    }

}