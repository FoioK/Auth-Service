package com.wojo.authservice.service.impl

import com.wojo.authservice.entity.VERIFICATION_PARAM_NAME
import com.wojo.authservice.entity.VERIFICATION_URI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service("emailSenderService")
class EmailSenderService @Autowired constructor(
        private val javaMailSender: JavaMailSender,
        private val envService: EnvService
) {

    @Async
    fun sendEmail(emailAddress: String, token: String) {
        javaMailSender.send(buildVerificationEmail(emailAddress, token))
    }

    private fun buildVerificationEmail(emailAddress: String, token: String): SimpleMailMessage {
        val mail = SimpleMailMessage()
        mail.setTo(emailAddress)
        mail.setSubject("Complete Registration!")
        mail.setText("To confirm your account, please click here: " +
                "${envService.getServerUrlPrefi()}/users$VERIFICATION_URI?$VERIFICATION_PARAM_NAME=$token")

        return mail
    }
}