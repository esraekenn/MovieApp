package com.example.movieapp.movie

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.network.model.Movie

class MovieAdapter(
    val movieList: List<Movie>,
    private val setOnClickListener: (recipe: Movie, position: Int) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position],setOnClickListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}