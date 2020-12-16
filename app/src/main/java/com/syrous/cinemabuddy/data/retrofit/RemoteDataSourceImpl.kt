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
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val  sysConfigStorage: SystemConfigUseCase
): RemoteDataSource {

    override fun fetchGenreList(): Flow<GenreResponse> = flow {
        moviesApi.getGenreList(BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang())
    }

    override fun fetchPopularMoviesList(page: Int): Flow<MovieResponse> = flow {
        moviesApi.getPopularMoviesList(
            BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang(),
            page, sysConfigStorage.getUserRegion()
        )
    }

    override fun fetchTopRatedMoviesList(page: Int): Flow<MovieResponse> = flow {
        moviesApi.getTopRatedMoviesList(
            BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang(),
            page, sysConfigStorage.getUserRegion()
        )
    }

    override fun fetchUpcomingMoviesList(page: Int): Flow<UpcomingMovieResponse> = flow {
        moviesApi.getUpcomingMoviesList(
            BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang(),
            page, sysConfigStorage.getUserRegion()
        )
    }

    override fun fetchMoviesDetails(movieId: Int): Flow<MovieDetailResponse> = flow {
        moviesApi.getMovieDetails(movieId, BuildConfig.API_KEY_V3, sysConfigStorage.getUserLang())
    }

}