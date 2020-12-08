package com.syrous.cinemabuddy.presentation.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syrous.cinemabuddy.R
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.presentation.common.ViewMVCFactory
import com.syrous.cinemabuddy.presentation.common.views.BaseObservableViewMVC
import com.syrous.cinemabuddy.presentation.home.moviecarousel.MovieCarouselAdapter


class HomeViewMVCImpl (
    inflater: LayoutInflater,
    parent: ViewGroup?,
    viewMVCFactory: ViewMVCFactory
) : BaseObservableViewMVC<HomeViewMVC.Listener>(), HomeViewMVC, MovieCarouselAdapter.Listener {

    private var movieCarousel: RecyclerView
    private var topRatedMovieCarouselAdapter: MovieCarouselAdapter
    private var popularMovieCarouselAdapter: MovieCarouselAdapter
    init {
        setRootView(inflater.inflate(R.layout.fragment_home, parent, false))

        movieCarousel = findViewById(R.id.movie_carousel)
        topRatedMovieCarouselAdapter = MovieCarouselAdapter(this, viewMVCFactory)
        popularMovieCarouselAdapter = MovieCarouselAdapter(this, viewMVCFactory)
        movieCarousel.apply {
            adapter = popularMovieCarouselAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    override fun bindTopRatedMovies(movieList: List<MovieDomainModel>) {
        topRatedMovieCarouselAdapter.submitList(movieList)
    }

    override fun bindPopularMovies(movieList: List<MovieDomainModel>) {
        popularMovieCarouselAdapter.submitList(movieList)
    }

    override fun onMovieClicked(movie: MovieDomainModel) {
        for(listeners in getListeners()) {
            listeners.onMovieClicked(movie)
        }
    }
}