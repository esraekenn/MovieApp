package com.example.movieapp.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.network.AppConstant
import com.example.movieapp.network.model.Search

class MovieSearchViewHolder(container: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(container.context).inflate
        (
        R.layout.row_movie_item, container, false
    )
) {
    val movieName: TextView = itemView.findViewById(R.id.txtMovieTitle)
    val movieImage: ImageView = itemView.findViewById(R.id.imgMovie)

    fun bind(searchMovie: Search) {
        movieName.text = searchMovie.title
        Glide.with(itemView)
            .load(AppConstant.MOVIE_IMAGE_URL + searchMovie.posterPath)
            .into(movieImage)

    }
}
