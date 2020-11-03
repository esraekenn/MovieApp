package com.example.movieapp.movie

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.network.model.Movie

class MovieListAdapter(
    val movieList: List<Movie>,
    private val setOnClickListener: (recipe: Movie, position: Int) -> Unit
) : RecyclerView.Adapter<MovieListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(parent)
    }

    override fun onBindViewHolder(holderList: MovieListViewHolder, position: Int) {
        holderList.bind(movieList[position],setOnClickListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}