package com.mycodeflow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycodeflow.data.*
import com.mycodeflow.repository.MovieListRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    val movieListRepository: MovieListRepository
    ): ViewModel() {

    private val mutableMoviesList = MutableLiveData<List<MovieListItem>>(emptyList())
    val moviesList: LiveData<List<MovieListItem>> get() = mutableMoviesList

    init{
        updateMovieData()
    }

    private fun updateMovieData(){
        viewModelScope.launch {
            mutableMoviesList.value = movieListRepository.getMovies(forceRefresh = false)
        }
    }
}