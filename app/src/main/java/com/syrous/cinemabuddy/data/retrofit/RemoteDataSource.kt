package com.syrous.cinemabuddy.data.retrofit

import com.syrous.cinemabuddy.data.retrofit.response.GenreResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieDetailResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.data.retrofit.response.UpcomingMovieResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun fetchGenreList(): Flow<GenreResponse>

    fun fetchPopularMoviesList(page: Int): Flow<MovieResponse>

    fun fetchTopRatedMoviesList(page: Int): Flow<MovieResponse>

    fun fetchUpcomingMoviesList(page: Int): Flow<UpcomingMovieResponse>

    fun fetchMoviesDetails(movieId: Int): Flow<MovieDetailResponse>
}