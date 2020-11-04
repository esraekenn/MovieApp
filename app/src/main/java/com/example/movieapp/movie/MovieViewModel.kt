package com.example.movieapp.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.network.MovieApi
import com.example.movieapp.network.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel: ViewModel() {

    val movieResponse: MutableLiveData<MovieResponse> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    private var movieCallback=object: Callback<MovieResponse> {
        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            errorMessage.value="failed"
        }
        override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
            movieResponse.value=response.body()
        }
    }

    fun getMovieList() {
        MovieApi.getMovie(movieCallback)
    }
}