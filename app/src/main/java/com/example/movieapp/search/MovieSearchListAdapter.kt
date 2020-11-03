package com.example.movieapp.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.network.model.Search

class MovieSearchListAdapter(
    val movieSearchList: List<Search>
) : RecyclerView.Adapter<MovieSearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchViewHolder {
        return MovieSearchViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        holder.bind(movieSearchList[position])
    }

    override fun getItemCount(): Int {
        return movieSearchList.size
    }
}
