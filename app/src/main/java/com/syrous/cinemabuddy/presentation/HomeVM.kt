package com.syrous.cinemabuddy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syrous.cinemabuddy.BuildConfig
import com.syrous.cinemabuddy.data.repository.MovieRepositoryImpl
import com.syrous.cinemabuddy.domain.model.GenreDomainModel
import com.syrous.cinemabuddy.domain.repository.MovieRepository
import com.syrous.cinemabuddy.domain.usecase.GetTopRatedMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeVM @Inject constructor(
private val movieRepository: MovieRepository
) : ViewModel() {
    fun getTheGenreList() {
        viewModelScope.launch{
            movieRepository.fetchAndCacheGenreList(BuildConfig.API_KEY_V3, "en-US")
        }
    }

    fun observeGenreData(): LiveData<List<GenreDomainModel>> =
        movieRepository.observeGenreData("en-US")
}