package com.example.movieapp.network

import android.app.Application
import android.content.Context
import com.example.movieapp.network.AppConstant.API_KEY
import com.example.movieapp.network.AppConstant.BASE_URL
import com.example.movieapp.network.model.MovieResponse
import com.example.movieapp.network.model.SearchResponse
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieApi {
    private var api: MovieApiService
    init {
    val retrofit:Retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        api=retrofit.create(MovieApiService::class.java)
    }

    fun getSearch(callBack:Callback<SearchResponse>,query:String) {
        api.getSearchMovieResponse( API_KEY,query).enqueue(callBack)
    }
    fun getMovie(callBack:Callback<MovieResponse>) {
        api.getMovieResponse( API_KEY).enqueue(callBack)
    }

}