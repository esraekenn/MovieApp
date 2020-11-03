package com.example.movieapp.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.network.AppConstant
import com.example.movieapp.network.model.Movie

class MovieViewHolder(container: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(container.context).inflate
        (
        R.layout.row_movie_item, container, false
    )
) {
    val movieTitle: TextView = itemView.findViewById(R.id.txtMovieTitle)
    val movieImage: ImageView = itemView.findViewById(R.id.imgMovie)

    fun bind(movie: Movie, setOnClickListener: (movie: Movie, position: Int) -> Unit) {
        movieTitle.text = movie.title
        Glide.with(itemView)
            .load(AppConstant.MOVIE_IMAGE_URL + movie.posterPath)
            .into(movieImage)
        itemView.setOnClickListener {
            setOnClickListener(movie, adapterPosition)
        }
    }
}