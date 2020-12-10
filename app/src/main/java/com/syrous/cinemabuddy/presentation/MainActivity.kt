package com.syrous.cinemabuddy.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.syrous.cinemabuddy.CinemaBuddyApplication
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.model.Result
import com.syrous.cinemabuddy.presentation.common.ViewMVCFactory
import com.syrous.cinemabuddy.presentation.common.controllers.BaseActivity
import com.syrous.cinemabuddy.presentation.home.HomeVM
import com.syrous.cinemabuddy.presentation.home.HomeViewMVC
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@InternalCoroutinesApi
class MainActivity: BaseActivity() {

    @Inject
    lateinit var viewModel : HomeVM

    @Inject
    lateinit var viewMVCFactory : ViewMVCFactory

    lateinit var viewMVC: HomeViewMVC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as CinemaBuddyApplication).appComponent.activitySubcomponent()
            .create(this).inject(this)
        viewMVC = viewMVCFactory.getHomeViewMVC(null)
        setContentView(viewMVC.getRootView())
    }

    override fun onStart() {
        super.onStart()

        viewModel.setChartedMovies(ChartType.TOP_RATED)
        viewModel.flowOfGenreList.asLiveData().observe(this) {
            when(it) {
                Result.NotInitialized -> viewModel.executeFetchGenreListUseCase()
                is Result.Success -> TODO()
                is Result.Error -> Toast.makeText(this, "Error: ${it.exception} occurred", Toast.LENGTH_SHORT).show()
                Result.Loading -> Toast.makeText(this, "Data is loading", Toast.LENGTH_SHORT).show()
                Result.DoneLoading -> {
                    viewModel.executeFetchChartedMoviesUseCase()
                }
            }
        }


        viewModel.flowOfChartedMovies.asLiveData().observe(this) {
            when(it) {
                Result.NotInitialized -> viewModel.loadChartedMoviesFromLocalStorage()
                is Result.Success -> viewMVC.bindPopularMovies(it.data)
                is Result.Error -> Toast.makeText(this, "Error: ${it.exception} occurred", Toast.LENGTH_SHORT).show()
                Result.Loading -> Toast.makeText(this, "Data is loading", Toast.LENGTH_SHORT).show()
                Result.DoneLoading -> Toast.makeText(this, "Data is loaded", Toast.LENGTH_SHORT).show()
            }
        }





    }
}