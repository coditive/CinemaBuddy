package com.syrous.cinemabuddy.presentation

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.asLiveData
import com.syrous.cinemabuddy.CinemaBuddyApplication
import com.syrous.cinemabuddy.presentation.common.ViewMVCFactory
import com.syrous.cinemabuddy.presentation.common.controllers.BaseActivity
import com.syrous.cinemabuddy.presentation.home.HomeVM
import com.syrous.cinemabuddy.presentation.home.HomeViewMVC
import kotlinx.coroutines.*
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

        viewModel.getTheGenreList()
        viewModel.getPopularMovie()

        viewModel.observePopularMovies().asLiveData().observe(this){
            moviesList ->
             viewMVC.bindPopularMovies(moviesList)
        }

    }
}