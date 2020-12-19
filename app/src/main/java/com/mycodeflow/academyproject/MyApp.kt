package com.mycodeflow.academyproject

import android.app.Application
import android.util.Log
import com.mycodeflow.datasource.JsonLoader
import com.mycodeflow.datasource.MoviesDataSource
import com.mycodeflow.datasource.MoviesDataSourceImpl

interface DataProvider {
    fun dataSource(): MoviesDataSource
}

class MyApp : Application(), DataProvider {

    private lateinit var moviesDataSource: MoviesDataSource
    private lateinit var moviesLoader: JsonLoader

    override fun onCreate() {
        super.onCreate()
        Log.d("myLogs", "MyApp instance created")
        moviesLoader = JsonLoader(this)
        moviesDataSource = MoviesDataSourceImpl(moviesLoader)
    }

    override fun dataSource(): MoviesDataSource = moviesDataSource
}