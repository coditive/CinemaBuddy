package com.syrous.cinemabuddy.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syrous.cinemabuddy.R
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.presentation.common.ViewMVCFactory
import com.syrous.cinemabuddy.presentation.common.views.BaseObservableViewMVC
import com.syrous.cinemabuddy.presentation.common.views.ObservableViewMVC

class HomeViewMVCImpl(
    inflater: LayoutInflater,
    parent: ViewGroup,
    viewMVCFactory: ViewMVCFactory
) : BaseObservableViewMVC<HomeViewMVC.Listener>(), HomeViewMVC {

    private lateinit var movieCarousel: RecyclerView

    init {
        setRootView(inflater.inflate(R.layout.fragment_home, parent, false))

        movieCarousel = findViewById(R.id.movie_carousel)


    }

    override fun bindTopRatedMovies(movieList: List<MovieDomainModel>) {
        TODO("Not yet implemented")
    }

    override fun bindPopularMovies(movieList: List<MovieDomainModel>) {
        TODO("Not yet implemented")
    }


    override fun getRootView(): View {
        TODO("Not yet implemented")
    }
}