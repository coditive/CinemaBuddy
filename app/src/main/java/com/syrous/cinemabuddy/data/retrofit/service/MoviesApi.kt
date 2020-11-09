package com.syrous.cinemabuddy.data.retrofit.service

import com.syrous.cinemabuddy.data.retrofit.response.GenreResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("/genre/movie/list")
    fun getGenreList(
        @Query("api_key") apiKey: String,
        @Query("language")lang: String,
    ): GenreResponse

    @GET("movie/popular")
    fun getPopularMoviesList(
            @Query("api_key") apiKey: String,
            @Query("language")lang: String,
            @Query("page") page: Int,
            @Query("region") region: String?
    ): MovieResponse


    @GET("movie/top_rated")
    fun getTopRatedMoviesList(
            @Query("api_key") apiKey: String,
            @Query("language")lang: String,
            @Query("page") page: Int,
            @Query("region") region: String?
    ): MovieResponse


    @GET("movie/upcoming")
    fun getUpcomingMovies(
            @Query("api_key") apiKey: String,
            @Query("language")lang: String,
            @Query("page") page: Int,
            @Query("region") region: String?
    ): MovieResponse

}