package com.wojo.authservice.controller

import com.wojo.authservice.entity.VERIFICATION_PARAM_NAME
import com.wojo.authservice.entity.VERIFICATION_URI
import com.wojo.authservice.model.UserInput
import com.wojo.authservice.model.UserResponse
import com.wojo.authservice.service.impl.CustomUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(
        private val userService: CustomUserDetailService
) {

    @PostMapping(
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun createUser(
            @Valid @RequestBody userInput: UserInput,
            @RequestParam("isNeedVerification", defaultValue = true.toString()) isNeedVerification: Boolean,
            request: HttpServletRequest
    ): ResponseEntity<UserResponse> =
            userService.createUser(userInput, isNeedVerification).let {
                ResponseEntity
                        .created(URI.create(request.requestURI + "/${it.code}"))
                        .body(it)
            }

    @RequestMapping(
            value = [VERIFICATION_URI],
            method = [RequestMethod.GET, RequestMethod.POST])
    fun confirmUserAccount(@RequestParam(VERIFICATION_PARAM_NAME) token: String) =
            if (userService.confirmUserAccount(token)) HttpStatus.OK
            else HttpStatus.BAD_REQUEST

}