package com.uratxe.core.serializer

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.joda.time.DateTime
import java.lang.reflect.Type

/**
 * Created by vssnake on 11/10/2017.
 */
class DateTimeSerialize : JsonSerializer<DateTime> {
    override fun serialize(
        src: DateTime,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}