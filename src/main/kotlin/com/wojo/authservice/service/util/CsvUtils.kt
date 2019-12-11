package com.wojo.authservice.service.util

import com.fasterxml.jackson.dataformat.csv.CsvMapper

import java.io.IOException
import java.io.InputStream

object CsvUtils {
    private val mapper = CsvMapper()

    @Throws(IOException::class)
    fun <T> read(clazz: Class<T>, stream: InputStream): Set<T> =
            run {
                val schema = mapper.schemaFor(clazz)
                val reader = mapper.readerFor(clazz).with(schema)

                reader.readValues<T>(stream)
                        .readAll()
                        .toSet()
            }
}