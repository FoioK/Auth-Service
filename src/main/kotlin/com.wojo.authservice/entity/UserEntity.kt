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
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(nullable = false, updatable = false, length = 40)
        val code: String = "",

        @Column(nullable = false, updatable = false)
        val email: String = "",

        @Column(nullable = false, updatable = false)
        val password: String = "",

        @Column(nullable = false)
        var userStatus: UserStatus = UserStatus.DEFAULT,

        @Column(nullable = false, updatable = false)
        val username: String = "",

        @Column(nullable = false, updatable = false)
        val createTime: LocalDateTime = LocalDateTime.MIN

) {

    @Transient
    var grantedAuthorityList: Collection<GrantedAuthority> = ArrayList()

}