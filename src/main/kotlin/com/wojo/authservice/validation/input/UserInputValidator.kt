package com.wojo.authservice.validation.input

import com.wojo.authservice.exception.ErrorCode
import com.wojo.authservice.exception.impl.DuplicateEmailException
import com.wojo.authservice.exception.impl.DuplicateUsernameException
import com.wojo.authservice.exception.impl.InvalidInputFieldValueException
import com.wojo.authservice.model.UserInput
import com.wojo.authservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


const val EMAIL_REGEXP = "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))\$"

@Component
class UserInputValidator @Autowired constructor(
        private val userRepository: UserRepository
) {

    fun checkDuplicates(userInput: UserInput) {
        val isEmailDuplicate: Boolean = userRepository.findByEmail(userInput.email).isPresent
        val isUsernameDuplicate: Boolean = userRepository.findByUsername(userInput.username).isPresent

        if (isEmailDuplicate) {
            throw DuplicateEmailException("Email already exist")
        }

        if (isUsernameDuplicate) {
            throw DuplicateUsernameException("Username already exist")
        }
    }

    fun checkDuplicatesForAll(users: Set<UserInput>) = users.forEach(this::checkDuplicates)

    fun validateUser(user: UserInput) {
        when {
            user.username.isBlank() ->
                throw InvalidInputFieldValueException("username is blank", ErrorCode.BLANK_USERNAME)
            user.password.isBlank() ->
                throw InvalidInputFieldValueException("password is blank", ErrorCode.BLANK_PASSWORD)
            user.email.isBlank() ->
                throw InvalidInputFieldValueException("email is blank", ErrorCode.BLANK_EMAIL)
            !user.email.matches(EMAIL_REGEXP.toRegex()) ->
                throw InvalidInputFieldValueException("invalid email format", ErrorCode.INVALID_EMAIL_FORMAT)
        }
    }

    fun validateAll(users: Set<UserInput>) =
            run {
                val userNames = mutableSetOf<String>()
                val userEmails = mutableSetOf<String>()

                users.stream().forEach { user ->
                    this.validateUser(user)

                    if (userNames.contains(user.username))
                        throw DuplicateUsernameException("Duplicate username")
                    else userNames.add(user.username)

                    if (userEmails.contains(user.email))
                        throw DuplicateEmailException("Duplicate email")
                    else userEmails.add(user.email)
                }
            }

}