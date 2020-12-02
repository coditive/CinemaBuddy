package com.syrous.cinemabuddy.data.local.TypeConverters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun fromDateToString(date: Date): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
       return try {
           synchronized(dateFormat) {
               dateFormat.format(date)
           }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @TypeConverter
    fun fromStringToDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            synchronized(dateFormat) {
                dateFormat.parse(dateString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}