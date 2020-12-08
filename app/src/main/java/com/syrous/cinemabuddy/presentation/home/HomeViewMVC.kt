package com.syrous.cinemabuddy.presentation.home

import com.syrous.cinemabuddy.data.local.model.MovieDBModel
import com.syrous.cinemabuddy.domain.model.ChartType
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.presentation.common.views.ObservableViewMVC

interface HomeViewMVC: ObservableViewMVC<HomeViewMVC.Listener>{
    interface Listener {
        fun onMovieClicked(movie: MovieDomainModel)
    }

    fun bindTopRatedMovies(movieList : List<MovieDomainModel>)

    fun bindPopularMovies(movieList : List<MovieDomainModel>)

}