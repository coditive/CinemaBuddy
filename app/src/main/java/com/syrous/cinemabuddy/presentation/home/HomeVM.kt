package com.syrous.cinemabuddy.presentation.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.usecases.FetchChartedMoviesUseCase
import com.syrous.cinemabuddy.domain.usecases.FetchGenreListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeVM @Inject constructor(
    private val fetchGenreListUseCase: FetchGenreListUseCase,
    private val fetchChartedMoviesUseCase: FetchChartedMoviesUseCase
    ) : ViewModel() {

    val flowOfGenreList = fetchGenreListUseCase.flowOfGenreList

    val flowOfChartedMovies = fetchChartedMoviesUseCase.flowOfChartedMovies

    fun executeFetchChartedMoviesUseCase() {
        viewModelScope.launch {
            fetchChartedMoviesUseCase.execute()
        }
    }

    fun executeFetchGenreListUseCase() {
        viewModelScope.launch {
            fetchGenreListUseCase.execute()
        }
    }

    fun loadGenreListFromLocalStorage() {
        viewModelScope.launch {
            fetchGenreListUseCase.getGenreList()
        }
    }

    fun loadChartedMoviesFromLocalStorage() {
        viewModelScope.launch {
            fetchChartedMoviesUseCase.loadChartedMoviesFromLocalStorage()
        }
    }

    fun setChartedMovies(chartType: ChartType) {
        viewModelScope.launch {
            fetchChartedMoviesUseCase.setChartType(chartType)
        }
    }

}