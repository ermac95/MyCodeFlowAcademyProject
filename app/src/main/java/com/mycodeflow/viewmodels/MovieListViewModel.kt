package com.mycodeflow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycodeflow.data.Movie
import com.mycodeflow.datasource.MoviesDataSource
import kotlinx.coroutines.launch

class MovieListViewModel(private val dataSource: MoviesDataSource): ViewModel() {

    private var mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())

    val moviesList: LiveData<List<Movie>> get() = mutableMoviesList

    init {
        loadMovies()
    }

    private fun loadMovies(){
        viewModelScope.launch {
            val newMoviesList = dataSource.getMoviesAsync()
            val updatedMoviesList = mutableMoviesList.value?.plus(newMoviesList).orEmpty()
            mutableMoviesList.value = updatedMoviesList
        }
    }
}