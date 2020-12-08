package com.syrous.cinemabuddy.presentation.home.moviecarousel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.syrous.cinemabuddy.R
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.presentation.common.views.BaseObservableViewMVC
import com.syrous.cinemabuddy.presentation.home.moviecarousel.MovieListItemViewMVC

class MovieListItemViewMVCImpl(inflater: LayoutInflater, parent: ViewGroup) :
    BaseObservableViewMVC<MovieListItemViewMVC.Listener>(), MovieListItemViewMVC {
    private lateinit var movie: MovieDomainModel
    private var movieTitle: TextView
    init {
        setRootView(inflater.inflate(R.layout.layout_movie_list_item, parent, false))
        getRootView().setOnClickListener {
            for(listener in getListeners()) {
                listener.onMovieClicked(movie)
            }
        }
        movieTitle = findViewById(R.id.movie_title)
    }

    override fun bindMovie(movie: MovieDomainModel) {
        this.movie = movie
        movieTitle.text = movie.title
    }
}