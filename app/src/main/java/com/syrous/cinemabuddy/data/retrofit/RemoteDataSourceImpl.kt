package com.syrous.cinemabuddy.data.retrofit

import android.os.Build
import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.data.retrofit.response.GenreResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieDetailResponse
import com.syrous.cinemabuddy.data.retrofit.response.MovieResponse
import com.syrous.cinemabuddy.data.retrofit.response.UpcomingMovieResponse
import com.syrous.cinemabuddy.data.retrofit.service.MoviesApi
import com.syrous.cinemabuddy.domain.usecases.SystemConfigUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val  sysConfigStorage: SystemConfigUseCase
): RemoteDataSource {

    override fun fetchGenreList(): Flow<RetrofitResult<GenreResponse>> = flow {
        try {
            val result = moviesApi.getGenreList(BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang())
            RetrofitResult.Success(result)
        } catch (e: HttpException) {
            RetrofitResult.NetworkError(e)
        }
    }

    override fun fetchPopularMoviesList(page: Int): Flow<RetrofitResult<MovieResponse>> = flow {
        try {
            val result = moviesApi.getPopularMoviesList(
                BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang(),
                page, sysConfigStorage.getUserRegion()
            )

            RetrofitResult.Success(result)
        }catch (e: HttpException){
            RetrofitResult.NetworkError(e)
        }
    }

    override fun fetchTopRatedMoviesList(page: Int): Flow<RetrofitResult<MovieResponse>> = flow {
        try {
           val result = moviesApi.getTopRatedMoviesList(
                BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang(),
                page, sysConfigStorage.getUserRegion()
            )
            RetrofitResult.Success(result)
        }catch (e: HttpException){
            RetrofitResult.NetworkError(e)
        }
    }

    override fun fetchUpcomingMoviesList(page: Int): Flow<RetrofitResult<UpcomingMovieResponse>> = flow {
        try {
            val result = moviesApi.getUpcomingMoviesList(
                BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang(),
                page, sysConfigStorage.getUserRegion()
            )
            RetrofitResult.Success(result)
        }catch (e: HttpException) {
            RetrofitResult.NetworkError(e)
        }
    }

    override fun fetchMoviesDetails(movieId: Int): Flow<RetrofitResult<MovieDetailResponse>> = flow {
        try {
            val result = moviesApi.getMovieDetails(movieId, BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang())
            RetrofitResult.Success(result)
        }catch (e: HttpException){
            RetrofitResult.NetworkError(e)
        }
    }

}