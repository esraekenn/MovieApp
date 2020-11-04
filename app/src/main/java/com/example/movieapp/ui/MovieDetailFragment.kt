package com.example.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailBinding
import com.example.movieapp.network.AppConstant
import com.example.movieapp.network.model.Movie

class MovieDetailFragment(val movieResponse: List<Movie>, val position: Int) :
    Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_detail, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestToTheService()
    }

    private fun requestToTheService() {
        val position = position
        val movieDetailList = movieResponse[position]
        binding.txtMovieTitle.text = movieDetailList.title
        binding.txtMovieDescription.text = movieDetailList.overview
        Glide.with(this)
            .load(AppConstant.MOVIE_IMAGE_URL + movieDetailList.posterPath)
            .into(binding.imgMovieDetail)
    }
}