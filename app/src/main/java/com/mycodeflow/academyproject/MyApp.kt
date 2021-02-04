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
        //starting periodic background cache update work with WorkManager
        bgWorkInit()
        setupDataSources()
    }

    private fun bgWorkInit(){
        applicationScope.launch {
            setupBackgroundWork(applicationContext)
        }
    }

    /*
    private suspend fun checkOneTimeState(){

        val workInfos = WorkManager.getInstance(applicationContext).getWorkInfosByTag("my_unique_work").await()
        if (workInfos.size == 1){
            val workInfo = workInfos[0]
            when(workInfo.state){
                WorkInfo.State.ENQUEUED -> Log.d("myLogs", "Work is enqueued")
                WorkInfo.State.RUNNING -> Log.d("myLogs", "Work is Running")
                WorkInfo.State.BLOCKED -> Log.d("myLogs", "Work is blocked")
                WorkInfo.State.SUCCEEDED -> Log.d("myLogs", "Work is succeeded")
                WorkInfo.State.FAILED -> Log.d("myLogs", "Work is Failed")
                WorkInfo.State.CANCELLED -> Log.d("myLogs", "Work is cancelled")
            }
        }
    }
    */

    /*
    private fun WorkManager.isAnyWorkScheduled(tag: String): Boolean {
        return try {
            getWorkInfosByTag(tag).get().firstOrNull { !it.state.isFinished } != null
        } catch (e: Exception) {
            when (e) {
                is ExecutionException, is InterruptedException -> {
                    e.printStackTrace()
                }
                else -> throw e
            }
            false
        }
    }
    */

    /*
    private suspend fun checkWorkState(workName: String){
        val workInfos = WorkManager.getInstance(applicationContext).getWorkInfosForUniqueWork(workName).await()
        if (workInfos.size == 1){
            val workInfo = workInfos[0]
            when(workInfo.state){
                WorkInfo.State.ENQUEUED -> Log.d("myLogs", "Work is enqueued")
                WorkInfo.State.RUNNING -> Log.d("myLogs", "Work is Running")
                WorkInfo.State.BLOCKED -> Log.d("myLogs", "Work is blocked")
                WorkInfo.State.SUCCEEDED -> Log.d("myLogs", "Work is succeeded")
                WorkInfo.State.FAILED -> Log.d("myLogs", "Work is Failed")
                WorkInfo.State.CANCELLED -> Log.d("myLogs", "Work is cancelled")
            }
        }
    }
    */

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

    private fun setupBackgroundWork(context: Context){
        val workConstraints = setConstraints() //setting work start constrains
        val cacheUpdateRequest = getPeriodicWorkRequest(workConstraints) //creating periodic work
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(CacheUpdateWork.CACHE_UPDATE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, cacheUpdateRequest) //starting unique periodic work
    }

    private fun setConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()
    }

    private fun getPeriodicWorkRequest(workConstraints: Constraints): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<CacheUpdateWork>(
            8,
            TimeUnit.HOURS)
            .setConstraints(workConstraints)
            .build()
    }

    override fun getFactory(): MovieListViewModelFactory = movieListFactory
}