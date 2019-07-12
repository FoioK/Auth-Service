package com.wojo.authservice.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service("emailSenderService")
class EmailSenderService @Autowired constructor(
        private val javaMailSender: JavaMailSender
) {

    @Async
    fun sendEmail(email: SimpleMailMessage) {
        javaMailSender.send(email)
    }

}