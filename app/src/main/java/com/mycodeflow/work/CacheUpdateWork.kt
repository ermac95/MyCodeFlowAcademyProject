package com.mycodeflow.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mycodeflow.api.TheMovieDBService
import com.mycodeflow.data.MovieListItem
import com.mycodeflow.datasource.TheMovieDataBase
import com.mycodeflow.repository.MovieListRepository
import com.mycodeflow.utils.MovieUpdateNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CacheUpdateWork(
    appContext: Context,
    params: WorkerParameters
) : Worker(appContext, params) {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun doWork(): Result {
        //if work fails to complete on the fourth time, it returns failure
        if (runAttemptCount > NUMBER_OF_ALLOWED_ATTEMPTS){
            return Result.failure()
        }
        val localDataSource = TheMovieDataBase.getInstance(context = applicationContext).getMovieDao()
        val remoteDataSource = TheMovieDBService.createService()
        val movieListRepository = MovieListRepository(remoteDataSource, localDataSource)
        coroutineScope.launch {
            val oldMoviesList = localDataSource.getAllMoviesList()
            try {
                movieListRepository.getMovies(true)
            } catch (e: IOException){
                Result.retry()
            }
            val newMoviesList = localDataSource.getAllMoviesList()
            if (oldMoviesList.isNotEmpty()) {
                val newMovie = checkNewMovieItem(oldMoviesList, newMoviesList)
                //checking if there is a new movie downloaded in comparison to previous data base if any
                if (newMovie != null){
                    val notificationLauncher = MovieUpdateNotification(applicationContext, newMovie)
                    notificationLauncher.sendNotification()
                }
            }
        }
        return Result.success()
    }

    private fun checkNewMovieItem(previousMovies: List<MovieListItem>, updatedMovies: List<MovieListItem>): MovieListItem?{
        val distinctMoviesList = updatedMovies.filterNot {movieItem ->
            previousMovies.any {
                movieItem.id == it.id
            }
        }
        return if (distinctMoviesList.isNotEmpty()){
            distinctMoviesList[0]
        } else {
            null
        }
    }

    companion object {
        const val CACHE_UPDATE_WORK_NAME = "background_cache_update_work"
        const val NUMBER_OF_ALLOWED_ATTEMPTS = 3
    }   
}
