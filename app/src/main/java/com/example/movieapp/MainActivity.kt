package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.movieapp.network.AppConstant
import com.example.movieapp.network.MovieApiService
import com.example.movieapp.network.RetrofitClient
import com.example.movieapp.network.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        deneme()
    }


    fun deneme() {
        RetrofitClient.getClient()
            .create(MovieApiService::class.java)
            .getMovieResponse(AppConstant.API_KEY).enqueue(object :
                Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.v("onFail", "${t.message}")
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    Log.v("onFail", "${response.body()}")
                }
            })
    }
}