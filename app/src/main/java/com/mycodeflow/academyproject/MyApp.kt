package com.mycodeflow.academyproject

import android.app.Application
import com.mycodeflow.api.MovieDataBaseService
import com.mycodeflow.api.TheMovieDBClient
import com.mycodeflow.datasource.MovieInteractor
import com.mycodeflow.datasource.TheMovieDao
import com.mycodeflow.datasource.TheMovieDataBase
import com.mycodeflow.repository.MovieDetailRepository
import com.mycodeflow.repository.MovieListRepository
import com.mycodeflow.viewmodels.MovieListViewModelFactory

interface DataProvider {
    fun getFactory(): MovieListViewModelFactory
}

class MyApp : Application(), DataProvider {

    private lateinit var movieRemoteDataBase: MovieDataBaseService
    private lateinit var movieRemoteDataSource: MovieInteractor
    private lateinit var movieLocalDataBase: TheMovieDataBase
    private lateinit var movieLocalDataSource: TheMovieDao
    private lateinit var movieListRepository: MovieListRepository
    private lateinit var movieDetailRepository: MovieDetailRepository
    private lateinit var movieListFactory: MovieListViewModelFactory

    override fun onCreate() {
        super.onCreate()
        //creating remote data source for future repository
        movieRemoteDataBase = TheMovieDBClient.getClient()
        movieRemoteDataSource = MovieInteractor(movieRemoteDataBase)
        //creating local data source for future repository
        movieLocalDataBase = TheMovieDataBase.getInstance(this)
        movieLocalDataSource = movieLocalDataBase.getMovieDao()
        //composing repository for sending in future viewModels
        movieListRepository = MovieListRepository(movieRemoteDataSource, movieLocalDataSource)
        movieDetailRepository = MovieDetailRepository(movieRemoteDataSource, movieLocalDataSource)
        movieListFactory = MovieListViewModelFactory(movieListRepository, movieDetailRepository)
    }

    override fun getFactory(): MovieListViewModelFactory = movieListFactory
}