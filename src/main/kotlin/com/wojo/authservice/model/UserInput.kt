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

)