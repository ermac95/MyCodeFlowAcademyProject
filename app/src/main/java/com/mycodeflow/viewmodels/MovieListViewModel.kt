package com.mycodeflow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycodeflow.data.*
import com.mycodeflow.repository.MovieListRepository
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieListRepository: MovieListRepository): ViewModel() {

    private val mutableMoviesList = MutableLiveData<List<MovieListModel>>(emptyList())
    val moviesList: LiveData<List<MovieListModel>> get() = mutableMoviesList

    init{
        updateMovieData()
    }

    private fun updateMovieData(){
        viewModelScope.launch {
            mutableMoviesList.value = movieListRepository.getMovies(forceRefresh = false)
        }
    }
}