package com.syrous.cinemabuddy.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syrous.cinemabuddy.R
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.presentation.common.ViewMVCFactory
import com.syrous.cinemabuddy.presentation.common.views.BaseObservableViewMVC
import com.syrous.cinemabuddy.presentation.common.views.ObservableViewMVC
import com.syrous.cinemabuddy.presentation.home.moviecarousel.MovieCarouselAdapter
import javax.inject.Inject

class HomeViewMVCImpl (
    inflater: LayoutInflater,
    parent: ViewGroup?,
    viewMVCFactory: ViewMVCFactory
) : BaseObservableViewMVC<HomeViewMVC.Listener>(), HomeViewMVC, MovieCarouselAdapter.Listener {

    private var movieCarousel: RecyclerView
    private var movieCarouselAdapter: MovieCarouselAdapter
    init {
        setRootView(inflater.inflate(R.layout.fragment_home, parent, false))

        movieCarousel = findViewById(R.id.movie_carousel)
        movieCarouselAdapter = MovieCarouselAdapter(this, viewMVCFactory)
        movieCarousel.apply {
            adapter = movieCarouselAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    override fun bindTopRatedMovies(movieList: List<MovieDomainModel>) {
        movieCarouselAdapter.submitList(movieList)
    }

    override fun bindPopularMovies(movieList: List<MovieDomainModel>) {
    }

    override fun onMovieClicked(movie: MovieDomainModel) {
        for(listeners in getListeners()) {
            listeners.onMovieClicked(movie)
        }
    }
}