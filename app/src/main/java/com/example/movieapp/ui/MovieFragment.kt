package com.example.movieapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.movie.MovieListAdapter
import com.example.movieapp.network.AppConstant
import com.example.movieapp.network.MovieApiService
import com.example.movieapp.network.RetrofitClient
import com.example.movieapp.network.model.Movie
import com.example.movieapp.network.model.MovieResponse
import com.example.movieapp.network.model.SearchResponse
import com.example.movieapp.search.MovieSearchListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


class MovieFragment : Fragment(), CoroutineScope {

    private lateinit var binding: FragmentMovieBinding
    lateinit var textWatcher: TextWatcher
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        requestToTheService()
        requestToSearchService()
        binding.searchBar.addTextChangedListener(textWatcher)
        return binding.root
    }

    fun requestToTheService() {
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
                    val movieList = response.body()!!.results
                    binding.recyclerView.layoutManager = GridLayoutManager(activity, 2)
                    binding.recyclerView.adapter =
                        MovieListAdapter(movieList) { movie: Movie, position: Int ->
                            val movieDetailFragment = MovieDetailFragment(response, position)
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.fragment, movieDetailFragment).commit()
                        }
                }
            })
    }

    private fun requestToSearchService() {
        textWatcher = object : TextWatcher {
            private var searchFor = ""
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText.length >= 3) {
                    if (searchText == searchFor)
                        return
                    searchFor = searchText
                    launch {
                        delay(1500)
                        if (searchText != searchFor)
                            return@launch
                        if (searchText.isNotEmpty()) {
                            RetrofitClient.getClient()
                                .create(MovieApiService::class.java)
                                .getSearchMovieResponse(AppConstant.API_KEY, searchText)
                                .enqueue(object : Callback<SearchResponse> {
                                    override fun onFailure(
                                        call: Call<SearchResponse>,
                                        t: Throwable
                                    ) {
                                        Log.v("onError", "$t.message")
                                    }

                                    override fun onResponse(
                                        call: Call<SearchResponse>,
                                        response: Response<SearchResponse>
                                    ) {
                                        val movieSearchList = response.body()!!.searches
                                        binding.recyclerView.layoutManager =
                                            GridLayoutManager(context, 2)
                                        binding.recyclerView.adapter =
                                            MovieSearchListAdapter(movieSearchList)
                                    }
                                })
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}