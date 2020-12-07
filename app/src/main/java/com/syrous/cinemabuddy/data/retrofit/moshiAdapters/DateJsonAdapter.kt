package com.syrous.cinemabuddy.data.retrofit.moshiAdapters


import com.squareup.moshi.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat

import java.util.*

class DateJsonAdapterFactory : JsonAdapter.Factory {
    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? = if (!Types.getRawType(type).isAssignableFrom(Date::class.java)) null
    else DateJsonAdapter()
}

class DateJsonAdapter: JsonAdapter<Date>() {
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault())
            synchronized(dateFormat) {
                dateFormat.parse(reader.nextString())
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun toJson(writer: JsonWriter, value: Date?) {
        //No-op
    }

    companion object {
        const val SERVER_DATE_FORMAT = "yyyy-MM-dd"
    }
}