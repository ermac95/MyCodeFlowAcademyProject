package com.mycodeflow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycodeflow.data.Movie
import com.mycodeflow.datasource.MoviesDataSource
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val dataSource: MoviesDataSource) : ViewModel() {
    private var mutableMovie = MutableLiveData<Movie>()

    val movieExample: LiveData<Movie> get() = mutableMovie

    fun getMovieById(movieId: Int){
        viewModelScope.launch {
            val newMovie = dataSource.getMovieByIdAsync(movieId)
            mutableMovie.value = newMovie
        }
    }
}