package com.wojo.authservice.entity

import org.springframework.security.core.GrantedAuthority
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class UserEntity(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column(nullable = false, updatable = false)
        val code: Long = 0,

        @Column(nullable = false, updatable = false)
        val email: String = "",

        @Column(nullable = false, updatable = false)
        val password: String = "",

        @Column(nullable = false)
        val isActive: Boolean = false,

        @Column(nullable = false, updatable = false)
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
            isActive = builder.isActive,
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
        var isActive: Boolean = false
        var nickname: String = ""
        var createTime: LocalDateTime = LocalDateTime.MIN

        fun build() = UserEntity(this)
    }

}