package com.syrous.cinemabuddy.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.syrous.cinemabuddy.R
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.presentation.common.views.BaseObservableViewMVC

class MovieListItemViewMVCImpl(inflater: LayoutInflater, parent: ViewGroup) :
    BaseObservableViewMVC<MovieListItemViewMVC.Listener>(), MovieListItemViewMVC {
    private lateinit var movie: MovieDomainModel
    init {
        setRootView(inflater.inflate(R.layout.layout_movie_list_item, parent, false))
        getRootView().setOnClickListener {
            for(listener in getListeners()) {
                listener.onMovieClicked(movie)
            }
        }
    }

    override fun bindMovie(movie: MovieDomainModel) {
        this.movie = movie
    }
}