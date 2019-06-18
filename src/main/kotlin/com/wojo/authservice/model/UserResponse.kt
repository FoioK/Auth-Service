package com.wojo.authservice.model

data class UserResponse(

        val code: String,
        val email: String,
        val username: String

) {

    private constructor(builder: Builder) : this(
            builder.code,
            builder.email,
            builder.username
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {

        var code: String = ""
        var email: String = ""
        var username: String = ""

        fun build() = UserResponse(this)
    }
}