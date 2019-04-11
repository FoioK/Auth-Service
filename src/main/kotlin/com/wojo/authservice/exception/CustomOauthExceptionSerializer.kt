package com.wojo.authservice.exception

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException


class CustomOauthExceptionSerializer : StdSerializer<CustomOAuthException>(CustomOAuthException::class.java) {

    @Throws(IOException::class)
    override fun serialize(exception: CustomOAuthException, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartObject()
        gen.writeStringField("error", exception.errorCode.name)
        gen.writeStringField("message", exception.message)
        gen.writeEndObject()
    }
}