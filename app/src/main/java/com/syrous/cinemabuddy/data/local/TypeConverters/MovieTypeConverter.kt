package com.syrous.cinemabuddy.data.local.TypeConverters

import androidx.room.TypeConverter
import com.syrous.cinemabuddy.domain.model.ChartType

class MovieTypeConverter {
    @TypeConverter
    fun fromMovieType(value: ChartType) : Int {
        return value.ordinal
    }

    @TypeConverter
    fun toMovieType(value: Int): ChartType {
       return when (value) {
           1 -> ChartType.POPULAR
           2 -> ChartType.TOP_RATED
           else -> ChartType.NORMAL
       }
    }

}