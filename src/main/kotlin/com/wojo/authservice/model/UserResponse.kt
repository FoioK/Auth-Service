package com.wojo.authservice.model

data class UserResponse(

        val id: Long,
        val code: String,
        val email: String,
        val nickName: String
) {

    private constructor(builder: Builder) : this(
            builder.id,
            builder.code,
            builder.email,
            builder.nickName
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {

        var id: Long = 0
        var code: String = ""
        var email: String = ""
        var nickName: String = ""

        fun build() = UserResponse(this)
    }
}