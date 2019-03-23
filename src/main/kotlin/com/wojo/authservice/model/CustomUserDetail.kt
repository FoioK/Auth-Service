package com.wojo.authservice.model

import com.wojo.authservice.entity.UserEntity
import org.springframework.security.core.userdetails.User
import java.time.LocalDateTime

class CustomUserDetail(user: UserEntity) :
        User(user.email, user.password, user.grantedAuthorityList) {

    private val code: Long = user.code
    private val email: String = user.email
    private val nickname: String = user.nickname
    private val createTime: LocalDateTime = user.createTime

}
