package com.wojo.authservice.model

import com.wojo.authservice.entity.UserEntity
import org.springframework.security.core.userdetails.User
import java.time.LocalDateTime

class CustomUserDetail(user: UserEntity) :
        User(user.username, user.password, user.grantedAuthorityList) {

    val code: String = user.code
    val email: String = user.email
    val createTime: LocalDateTime = user.createTime

}
