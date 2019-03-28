package com.wojo.authservice.exception

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

import java.io.IOException
import java.util.Arrays

class CustomOauthExceptionSerializer : StdSerializer<CustomOAuthException>(CustomOAuthException::class.java) {

    @Throws(IOException::class)
    override fun serialize(value: CustomOAuthException, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
        jsonGenerator.writeStartObject()
        jsonGenerator.writeNumberField("code", value.httpErrorCode)
        jsonGenerator.writeBooleanField("status", false)
//        jsonGenerator.writeObjectField("X-Code", value.getCodeX())
        jsonGenerator.writeObjectField("errors", Arrays.asList<String>(value.oAuth2ErrorCode, value.message))
        if (value.additionalInformation != null) {
            for ((key, add) in value.additionalInformation) {
                jsonGenerator.writeStringField(key, add)
            }
        }
        jsonGenerator.writeEndObject()
    }
}