package com.wojo.authservice.model

import com.wojo.authservice.entity.UserEntity
import org.springframework.security.core.userdetails.User
import java.time.LocalDateTime

class CustomUserDetail(user: UserEntity) :
        User(user.email, user.password, user.grantedAuthorityList) {

    val code: Long = user.code
    val nickname: String = user.nickname
    val createTime: LocalDateTime = user.createTime

}
