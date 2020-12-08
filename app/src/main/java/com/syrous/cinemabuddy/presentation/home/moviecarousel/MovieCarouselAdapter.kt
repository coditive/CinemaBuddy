package com.syrous.cinemabuddy.presentation.home.moviecarousel

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syrous.cinemabuddy.domain.model.MovieDomainModel
import com.syrous.cinemabuddy.presentation.common.ViewMVCFactory

class MovieCarouselAdapter(
    private val listener: Listener,
    private val viewMVCFactory: ViewMVCFactory
): ListAdapter<MovieDomainModel, MovieCarouselAdapter.MovieViewHolder>(CALLBACK),
    MovieListItemViewMVC.Listener {

    interface Listener {
        fun onMovieClicked(movie: MovieDomainModel)
    }

    inner class MovieViewHolder(private val viewMVC: MovieListItemViewMVC)
        : RecyclerView.ViewHolder(viewMVC.getRootView()) {
            fun bindMovieToHolder(movie: MovieDomainModel) {
                viewMVC.bindMovie(movie)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
       val viewMvc = viewMVCFactory.getMovieListItemViewMVC(parent)
        viewMvc.registerListener(this)
        return MovieViewHolder(viewMvc)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovieToHolder(getItem(position))
    }


    override fun onMovieClicked(movie: MovieDomainModel) {
        listener.onMovieClicked(movie)
    }

    companion object {
       val CALLBACK = object: DiffUtil.ItemCallback<MovieDomainModel>() {
           override fun areItemsTheSame(
               oldItem: MovieDomainModel,
               newItem: MovieDomainModel
           ): Boolean = oldItem.id == newItem.id

           override fun areContentsTheSame(
               oldItem: MovieDomainModel,
               newItem: MovieDomainModel
           ): Boolean = oldItem.createdAt == newItem.createdAt
       }
    }
}