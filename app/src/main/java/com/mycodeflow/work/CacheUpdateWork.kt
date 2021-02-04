package com.mycodeflow.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mycodeflow.api.TheMovieDBClient
import com.mycodeflow.datasource.MovieInteractor
import com.mycodeflow.datasource.TheMovieDataBase
import com.mycodeflow.repository.MovieListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CacheUpdateWork(
    appContext: Context,
    params: WorkerParameters
) : Worker(appContext, params) {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun doWork(): Result {
        Log.d("myLogs", "update work started")
        val localDataSource = TheMovieDataBase.getInstance(context = applicationContext).getMovieDao()
        val remoteDataSource = MovieInteractor(TheMovieDBClient.getClient())
        val movieListRepository = MovieListRepository(remoteDataSource, localDataSource)
        try{
            coroutineScope.launch {
                movieListRepository.getMovies(true)
            }
        } catch (e: HttpException){
            return Result.retry()
        }
        return Result.success()
    }

    companion object {
        const val CACHE_UPDATE_WORK_NAME = "background_cache_update_work"
    }   
}