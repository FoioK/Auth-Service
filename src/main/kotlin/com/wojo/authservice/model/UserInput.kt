package com.wojo.authservice.model

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserInput(

        @field:NotBlank
        @field:Email
        val email: String = "",

        @field:NotBlank
        val password: String = "",

        @field:NotBlank
        val nickname: String = ""
) {

    private constructor(builder: Builder) : this(
            builder.email,
            builder.password,
            builder.nickName
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {

        var email: String = ""
        var password: String = ""
        var nickName: String = ""

        fun build() = UserInput(this)
    }
}