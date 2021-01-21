package com.mycodeflow.academyproject

import android.app.Application
import android.util.Log
import com.mycodeflow.api.MovieDataBaseService
import com.mycodeflow.api.TheMovieDBClient
import com.mycodeflow.datasource.MovieInteractor
import com.mycodeflow.viewmodels.MovieListViewModelFactory

interface DataProvider {
    fun getFactory(): MovieListViewModelFactory
}

class MyApp : Application(), DataProvider {

    private lateinit var movieDataBase: MovieDataBaseService
    private lateinit var movieInteractor: MovieInteractor
    private lateinit var movieListFactory: MovieListViewModelFactory

    override fun onCreate() {
        super.onCreate()
        Log.d("myLogs", "MyApp instance created")
        movieDataBase = TheMovieDBClient.getClient()
        movieInteractor = MovieInteractor(movieDataBase)
        movieListFactory = MovieListViewModelFactory(movieInteractor)
    }

    override fun getFactory(): MovieListViewModelFactory = movieListFactory
}