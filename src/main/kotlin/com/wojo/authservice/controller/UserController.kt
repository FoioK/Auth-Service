package com.wojo.authservice.controller

import com.wojo.authservice.model.UserDetails
import com.wojo.authservice.model.UserInput
import com.wojo.authservice.service.impl.CustomUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(
        private val userService: CustomUserDetailService
){

    @PostMapping(
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
    )
    fun createUser(@Valid @RequestBody userInput: UserInput): ResponseEntity<UserDetails> {
            val userDetails: UserDetails = userService.createUser(userInput)

        return ResponseEntity.created(URI.create()).body(userDetails)
    }
}