package com.wojo.authservice.model

enum class UserStatus(val numer: Int = 0) {

    CREATED(1),
    PENDING(2),
    ACTIVE(3),
    MODIFIED(4),
    BLOCKED(5),
    DELETED(6),

}