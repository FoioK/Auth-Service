package com.wojo.authservice.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class UserInput(

        @field:NotBlank
        @field:Pattern(
                message = "Email invalid",
                regexp = "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))\$")
        val email: String = "",

        @field:NotBlank
        val password: String = "",

        @field:NotBlank
        val username: String = ""
) {

    private constructor(builder: Builder) : this(
            builder.email,
            builder.password,
            builder.username
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {

        var email: String = ""
        var password: String = ""
        var username: String = ""

        fun build() = UserInput(this)
    }
}