package com.wojo.authservice.model

data class UserDetails(

        val id: Long,
        val code: Long,
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
        var code: Long = 0
        var email: String = ""
        var nickName: String = ""

        fun build() = UserDetails(this)
    }
}