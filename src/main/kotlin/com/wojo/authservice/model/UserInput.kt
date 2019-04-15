package com.wojo.authservice.model

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserInput(

        @NotBlank
        @Email
        val email: String,

        @NotBlank
        val password: String,

        @NotBlank
        val nickName: String
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