package com.syrous.cinemabuddy.presentation.common

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.syrous.cinemabuddy.presentation.home.MovieListItemViewMVC
import com.syrous.cinemabuddy.presentation.home.MovieListItemViewMVCImpl
import javax.inject.Inject

class ViewMVCFactory @Inject constructor(
    private val layoutInflater: LayoutInflater) {

    fun getMovieListItemViewMVC(@Nullable parent: ViewGroup): MovieListItemViewMVC =
        MovieListItemViewMVCImpl(layoutInflater, parent)


}