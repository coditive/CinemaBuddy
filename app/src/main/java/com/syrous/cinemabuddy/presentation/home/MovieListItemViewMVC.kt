package com.syrous.cinemabuddy.presentation.home

import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.presentation.common.views.ObservableViewMVC

interface MovieListItemViewMVC : ObservableViewMVC<MovieListItemViewMVC.Listener>{
    interface Listener {
        fun onMovieClicked(movie: MovieDomainModel)
    }

    fun bindMovie(movie: MovieDomainModel)
}