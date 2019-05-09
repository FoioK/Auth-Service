package com.wojo.authservice.validation.input

import com.wojo.authservice.exception.impl.DuplicateEmailException
import com.wojo.authservice.exception.impl.DuplicateNicknameException
import com.wojo.authservice.model.UserInput
import com.wojo.authservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CheckUserDuplicates @Autowired constructor(
        private val userRepository: UserRepository
) {
    fun checkDuplicates(userInput: UserInput) {
        val isEmailDuplicate: Boolean = userRepository.findByEmail(userInput.email).isPresent
        val isNicknameDuplicate: Boolean = userRepository.findByNickname(userInput.nickName).isPresent

        if (isEmailDuplicate) {
            throw DuplicateEmailException("Email already exist")
        }

        if (isNicknameDuplicate) {
            throw DuplicateNicknameException("Nickname already exist")
        }
    }
}