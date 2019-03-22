package com.wojo.authservice.entity

import com.wojo.authservice.model.UserStatus
import org.springframework.security.core.GrantedAuthority
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user")
data class UserEntity(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column(nullable = false, updatable = false, unique = true)
        val code: Long = 0,

        @Column(nullable = false, updatable = false, unique = true)
        val email: String = "",

        @Column(nullable = false, updatable = false)
        val password: String = "",

        @Column(nullable = false)
        val userStatus: UserStatus = UserStatus.CREATED,

        @Column(nullable = false, updatable = false, unique = true)
        val nickname: String = "",

        @Column(nullable = false, updatable = false)
        val createTime: LocalDateTime = LocalDateTime.MIN

) {

    @Transient
    var grantedAuthorityList: Collection<GrantedAuthority> = ArrayList()

    private constructor(builder: Builder) : this(
            id = builder.id,
            code = builder.code,
            email = builder.email,
            password = builder.password,
            userStatus = builder.userStatus,
            nickname = builder.nickname,
            createTime = builder.createTime
    )

    companion object {
        inline fun build(block: Permission.Builder.() -> Unit) = Permission.Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var code: Long = 0
        var email: String = ""
        var password: String = ""
        var userStatus: UserStatus = UserStatus.CREATED
        var nickname: String = ""
        var createTime: LocalDateTime = LocalDateTime.MIN

        fun build() = UserEntity(this)
    }

}