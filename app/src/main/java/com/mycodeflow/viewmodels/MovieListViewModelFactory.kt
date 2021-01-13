package com.mycodeflow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycodeflow.datasource.MovieInteractor
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MovieListViewModelFactory(private val moviesInteractor: MovieInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass){
        MovieListViewModel::class.java -> MovieListViewModel(moviesInteractor)
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(moviesInteractor)
        else -> throw IllegalArgumentException("wrong dependencies")
    } as T
}