package com.syrous.cinemabuddy.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkRequest
import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.backgroundwork.enqueueSubscriptionWorker
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeVM @Inject constructor(
    private val context: Application,
    private val movieRepository: MovieRepository
    ) : ViewModel() {
    fun getTheGenreList() {
        viewModelScope.launch{
            movieRepository.fetchAndCacheGenreList(BuildConfig.API_KEY_V3, "en-US")
        }
    }

    fun observeGenreData(): Flow<List<GenreDomainModel>> =
        movieRepository.observeGenreData("en-US")


    fun observeTopRatedMovies(): Flow<List<MovieDomainModel>> =
        movieRepository.observeChartedMovies(ChartType.TOP_RATED, 0)

    fun getTopRatedMovie() {
        viewModelScope.launch {
            movieRepository.fetchAndCacheTopRateMovies(BuildConfig.API_KEY_V3, "en-US", 1, null)
        }
    }

    fun observePopularMovies(): Flow<List<MovieDomainModel>> =
        movieRepository.observeChartedMovies(ChartType.POPULAR, 10)

    fun getPopularMovie() {
        viewModelScope.launch {
            movieRepository.fetchAndCachePopularMovies(BuildConfig.API_KEY_V3, "en-US", 1, null)
        }
    }

    fun getMovieDetails() {
        viewModelScope.launch {
            movieRepository.fetchMovieDetails(240, BuildConfig.API_KEY_V3, "en-US")
        }
    }
    

}