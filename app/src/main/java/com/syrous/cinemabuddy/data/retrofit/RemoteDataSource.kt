package com.syrous.cinemabuddy.data.retrofit

import com.syrous.cinemabuddy.data.retrofit.response.GenreResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieDetailResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.data.retrofit.response.UpcomingMovieResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun fetchGenreList(): Flow<RetrofitResult<GenreResponse>>

    fun fetchPopularMoviesList(page: Int): Flow<RetrofitResult<MovieResponse>>

    fun fetchTopRatedMoviesList(page: Int): Flow<RetrofitResult<MovieResponse>>

    fun fetchUpcomingMoviesList(page: Int): Flow<RetrofitResult<UpcomingMovieResponse>>

    fun fetchMoviesDetails(movieId: Int): Flow<RetrofitResult<MovieDetailResponse>>
}