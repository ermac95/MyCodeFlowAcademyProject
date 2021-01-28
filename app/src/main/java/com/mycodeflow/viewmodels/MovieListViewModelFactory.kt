package com.mycodeflow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycodeflow.repository.MovieDetailRepository
import com.mycodeflow.repository.MovieListRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MovieListViewModelFactory(
    private val moviesListRepository: MovieListRepository, 
    private val moviesDetailRepository: MovieDetailRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass){
        MovieListViewModel::class.java -> MovieListViewModel(moviesListRepository)
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(moviesDetailRepository)
        else -> throw IllegalArgumentException("wrong dependencies")
    } as T
}
