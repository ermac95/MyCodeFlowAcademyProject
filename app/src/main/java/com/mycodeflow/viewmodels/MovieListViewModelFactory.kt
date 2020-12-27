package com.mycodeflow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycodeflow.datasource.MoviesDataSource
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MovieListViewModelFactory(private val dataSource: MoviesDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass){
        MovieListViewModel::class.java -> MovieListViewModel(dataSource)
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(dataSource)
        else -> throw IllegalArgumentException("wrong dependencies")
    } as T
}