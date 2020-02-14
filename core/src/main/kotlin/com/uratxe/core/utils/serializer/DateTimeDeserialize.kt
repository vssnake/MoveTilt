package com.baturamobile.utils.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import org.joda.time.DateTime
import java.lang.reflect.Type

/**
 * Created by vssnake on 11/10/2017.
 */
class DateTimeDeserialize : JsonDeserializer<DateTime> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): DateTime {
        return DateTime(json.asJsonPrimitive.asString)
    }
}