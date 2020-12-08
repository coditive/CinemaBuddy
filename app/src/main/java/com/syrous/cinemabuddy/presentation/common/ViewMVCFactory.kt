package com.syrous.cinemabuddy.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.syrous.cinemabuddy.presentation.home.HomeViewMVC
import com.syrous.cinemabuddy.presentation.home.HomeViewMVCImpl
import com.syrous.cinemabuddy.presentation.home.moviecarousel.MovieListItemViewMVC
import com.syrous.cinemabuddy.presentation.home.moviecarousel.MovieListItemViewMVCImpl
import javax.inject.Inject

class ViewMVCFactory @Inject constructor(
    private val layoutInflater: LayoutInflater) {

    fun getMovieListItemViewMVC(@Nullable parent: ViewGroup): MovieListItemViewMVC =
        MovieListItemViewMVCImpl(layoutInflater, parent)

    fun getHomeViewMVC(@Nullable parent: ViewGroup?): HomeViewMVC =
        HomeViewMVCImpl(layoutInflater, parent, this)
}