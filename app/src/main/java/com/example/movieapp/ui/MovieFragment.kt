package com.example.movieapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.movie.MovieListAdapter
import com.example.movieapp.movie.MovieViewModel
import com.example.movieapp.network.AppConstant
import com.example.movieapp.network.AppConstant.EMPTY_STRING
import com.example.movieapp.network.AppConstant.WORD_COUNT
import com.example.movieapp.network.model.Movie
import com.example.movieapp.network.model.MovieResponse
import com.example.movieapp.network.model.SearchResponse
import com.example.movieapp.search.MovieSearchListAdapter
import com.example.movieapp.search.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class MovieFragment : Fragment(), CoroutineScope {

    private val searchViewModel: SearchViewModel = SearchViewModel()
    private val movieViewModel: MovieViewModel = MovieViewModel()
    private lateinit var binding: FragmentMovieBinding
    lateinit var textWatcher: TextWatcher
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        movieLiveData()
        searchLiveData()
        requestToSearchService()
        binding.searchBar.addTextChangedListener(textWatcher)
        return binding.root
    }

    private fun requestToSearchService() {
        textWatcher = object : TextWatcher {
            private var searchFor = EMPTY_STRING
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText.length >= WORD_COUNT) {
                    if (searchText == searchFor)
                        return
                    searchFor = searchText
                    launch {
                        delay(1500)
                        if (searchText != searchFor)
                            return@launch
                        if (searchText.isNotEmpty()) {

                            searchViewModel.getSearchList(searchText)
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit
        }
    }

    fun searchLiveData() {
        searchViewModel.searchResponse.observe(viewLifecycleOwner, Observer { searchResponse ->
            searchResponse?.let { search ->
                initializeSearchRecyclerView(searchResponse)
            }
        })
    }

    fun initializeSearchRecyclerView(searchResponse: SearchResponse) {
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2)
        val searchList = searchResponse.searches
        binding.recyclerView.adapter =
            MovieSearchListAdapter(searchList)
    }

    fun initializeMovieRecyclerView(movieResponse: MovieResponse) {
        val movieList = movieResponse.results
        binding.recyclerView.adapter = MovieListAdapter(movieList) { movie: Movie, position: Int ->
            val movieDetailFragment = MovieDetailFragment(movieList, position)
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment, movieDetailFragment)?.addToBackStack(null)?.commit()
        }
    }

    fun movieLiveData() {
        movieViewModel.apply {
            getMovieList()
        }.movieResponse.observe(viewLifecycleOwner, Observer { movieResponse ->
            movieResponse?.let { movie ->
                binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
                initializeMovieRecyclerView(movieResponse)
            }
        })
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}