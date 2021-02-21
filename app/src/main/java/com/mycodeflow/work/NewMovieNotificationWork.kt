package com.mycodeflow.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mycodeflow.data.MovieListItem
import com.mycodeflow.datasource.TheMovieDataBase
import com.mycodeflow.utils.MovieUpdateNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//test class for showing notification with a current movie with highest rating
class NewMovieNotificationWork(
        appContext: Context,
        parameters: WorkerParameters
) : Worker(appContext, parameters) {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun doWork(): Result {
        val localDataSource = TheMovieDataBase.getInstance(context = applicationContext).getMovieDao()
        coroutineScope.launch {
            val movies = localDataSource.getAllMoviesList()
            val maxRatingMovie = findTheMostPopularMovie(movies)
            if (maxRatingMovie!= null){
                val notificationLauncher = MovieUpdateNotification(applicationContext, maxRatingMovie)
                notificationLauncher.sendNotification()
            }
        }
        return Result.success()
    }

    private fun findTheMostPopularMovie(movies: List<MovieListItem>): MovieListItem?{
        return movies.maxByOrNull {
            it.ratings
        }
    }
}