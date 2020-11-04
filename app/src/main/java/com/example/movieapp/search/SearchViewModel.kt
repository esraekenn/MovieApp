package com.example.movieapp.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.network.MovieApi
import com.example.movieapp.network.MovieApiService
import com.example.movieapp.network.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    val searchResponse: MutableLiveData<SearchResponse> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    private var searchCallback=object: Callback<SearchResponse>{
        override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
            errorMessage.value="failed"
        }
        override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
        searchResponse.value=response.body()
        }
    }

    fun getSearchList(query:String) {
        MovieApi.getSearch(searchCallback,query)
    }
}