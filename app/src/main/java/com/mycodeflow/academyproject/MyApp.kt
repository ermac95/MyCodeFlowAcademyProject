package com.mycodeflow.academyproject

import android.app.Application
import android.util.Log
import com.mycodeflow.datasource.JsonLoader
import com.mycodeflow.datasource.MoviesDataSource
import com.mycodeflow.datasource.MoviesDataSourceImpl
import com.mycodeflow.viewmodels.MovieListViewModelFactory

interface DataProvider {
    fun getFactory(): MovieListViewModelFactory
}

class MyApp : Application(), DataProvider {

    private lateinit var moviesDataSource: MoviesDataSource
    private lateinit var moviesLoader: JsonLoader
    private lateinit var movieListFactory: MovieListViewModelFactory

    override fun onCreate() {
        super.onCreate()
        Log.d("myLogs", "MyApp instance created")
        moviesLoader = JsonLoader(this)
        moviesDataSource = MoviesDataSourceImpl(moviesLoader)
        movieListFactory = MovieListViewModelFactory(moviesDataSource)
    }

    override fun getFactory(): MovieListViewModelFactory = movieListFactory
}