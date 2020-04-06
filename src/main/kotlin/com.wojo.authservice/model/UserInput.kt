package com.wojo.authservice.model

import com.wojo.authservice.validation.input.EMAIL_REGEXP
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class UserInput(

        @field:NotBlank
        @field:Pattern(
                message = "Email invalid",
                regexp = EMAIL_REGEXP)
        val email: String = "",

        @field:NotBlank
        val password: String = "",

        @field:NotBlank
        val username: String = ""

)