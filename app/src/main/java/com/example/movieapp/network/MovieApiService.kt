package com.example.movieapp.network

import com.example.movieapp.network.model.MovieResponse
import com.example.movieapp.network.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    fun getMovieResponse(
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    @GET("search/movie")
    fun getSearchMovieResponse(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<SearchResponse>
}
