package com.syrous.cinemabuddy.data.retrofit.service

import com.syrous.cinemabuddy.data.retrofit.response.GenreResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieDetailResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.data.retrofit.response.UpcomingMovieResponse
import retrofit2.http.*

interface MoviesApi {

    @GET("genre/movie/list")
   suspend fun getGenreList(
        @Query("api_key") apiKey: String,
        @Query("language")lang: String,
    ): GenreResponse

    @GET("movie/popular")
   suspend fun getPopularMoviesList(
            @Query("api_key") apiKey: String,
            @Query("language")lang: String,
            @Query("page") page: Int,
            @Query("region") region: String?
    ): MovieResponse


    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesList(
            @Query("api_key") apiKey: String,
            @Query("language")lang: String,
            @Query("page") page: Int,
            @Query("region") region: String?
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesList(
            @Query("api_key") apiKey: String,
            @Query("language")lang: String,
            @Query("page") page: Int,
            @Query("region") region: String?
    ): UpcomingMovieResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language")lang: String
    ): MovieDetailResponse

}