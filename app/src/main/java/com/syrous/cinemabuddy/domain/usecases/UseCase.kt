package com.syrous.cinemabuddy.domain.usecases

import com.syrous.cinemabuddy.data.local.*
import com.syrous.cinemabuddy.data.retrofit.response.MovieDetailResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.domain.model.ChartType

interface UseCase {

    suspend fun execute()

}