package com.mycodeflow.academyproject

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.*
import com.mycodeflow.api.MovieDataBaseService
import com.mycodeflow.api.TheMovieDBClient
import com.mycodeflow.datasource.MovieInteractor
import com.mycodeflow.datasource.TheMovieDao
import com.mycodeflow.datasource.TheMovieDataBase
import com.mycodeflow.repository.MovieDetailRepository
import com.mycodeflow.repository.MovieListRepository
import com.mycodeflow.viewmodels.MovieListViewModelFactory
import com.mycodeflow.work.CacheUpdateWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

interface DataProvider {
    fun getFactory(): MovieListViewModelFactory
}

class MyApp : Application(), DataProvider {

    //coroutine scope for launching coroutine work
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private lateinit var movieRemoteDataBase: MovieDataBaseService
    private lateinit var movieRemoteDataSource: MovieInteractor
    private lateinit var movieLocalDataBase: TheMovieDataBase
    private lateinit var movieLocalDataSource: TheMovieDao
    private lateinit var movieListRepository: MovieListRepository
    private lateinit var movieDetailRepository: MovieDetailRepository
    private lateinit var movieListFactory: MovieListViewModelFactory

    override fun onCreate() {
        super.onCreate()
        setupDataSources()
    }

    private fun setupDataSources(){
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