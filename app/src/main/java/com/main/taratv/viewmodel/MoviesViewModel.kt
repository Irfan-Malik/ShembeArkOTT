package com.main.taratv.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.main.taratv.data.MockApi
import com.main.taratv.screens.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            val ctx = getApplication<Application>().applicationContext
            val fetched = MockApi.fetchMovies(ctx)
//            if (fetched.isNotEmpty()) {
//                _movies.value = fetched
//            } else {
                // fallback to local data defined in MoviesScreen file
                _movies.value = com.main.taratv.screens.getAllMovies()
//            }
        }
    }

    fun refresh() {
        loadMovies()
    }
}

