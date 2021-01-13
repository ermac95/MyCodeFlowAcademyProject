package com.mycodeflow.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycodeflow.data.*
import com.mycodeflow.datasource.MovieInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(private val dataSource: MovieInteractor) : ViewModel() {
    private var mutableMovie = MutableLiveData<Movie>()
    val movieExample: LiveData<Movie> get() = mutableMovie

    private var actors: List<Actor>? = null
    private var baseImageUrl: String? = null
    private lateinit var newMovie: MovieDetailsResponse

    private suspend fun loadCast(movieId: Int) = withContext(Dispatchers.IO){
        val actorsData = dataSource.loadMovieCast(movieId)
        Log.d(TAG, "actorsData = $actorsData")
        val actorsList = actorsData.cast
        Log.d(TAG, "actorsList = $actorsList")
        actors = parseActors(actorsList)
        Log.d(TAG, "final actors = $actors")
    }

    private fun parseActors(actorData: List<Cast>): List<Actor>{
        return actorData.map { Actor(id = it.id.toInt(), name = it.name, profilePicture = "${baseImageUrl}w200${it.profilePath}") }
    }

    private suspend fun createImageBaseUrl() = withContext(Dispatchers.IO){
        val imageConfig = dataSource.loadImageConfig()
        Log.d(TAG, "imageConfig = $imageConfig")
        val imageBaseUrl = imageConfig.images.secureBaseURL
        baseImageUrl = imageBaseUrl
        Log.d(TAG, "final imageBaseUrl = $imageBaseUrl")
    }

    private suspend fun loadMovieSelected(movieId: Int) = withContext(Dispatchers.IO){
        newMovie = dataSource.loadMovieById(movieId)
        Log.d(TAG, "newMovie = $newMovie")
    }

    fun loadMovieById(movieId: Int){
        viewModelScope.launch(Dispatchers.Main) {
            Log.d(TAG, "Load cast and creating basic image url is launched")
            loadMovieSelected(movieId)
            createImageBaseUrl()
            loadCast(movieId)
            val updatedMovie = composeMovie(newMovie)
            Log.d(TAG, "Movie composing is finished, updatedMovie var is $updatedMovie")
            mutableMovie.value = updatedMovie
            Log.d(TAG, "main coroutine is finished")
        }
    }

    private fun composeMovie(
        movie: MovieDetailsResponse
    ): Movie{
        Log.d(TAG, "actorsForParse = ${actors.toString()}")
        Log.d(TAG, "baseImageUrl for parse = ${baseImageUrl.toString()}")
        return Movie(
            id = movie.id.toInt(),
            title = movie.originalTitle,
            overview = movie.overview,
            poster = "${baseImageUrl}w200${movie.posterPath}",
            backdrop = "${baseImageUrl}w200${movie.backdropPath}",
            ratings = movie.voteAverage,
            numberOfRatings = movie.voteCount.toInt(),
            minimumAge = if (movie.adult) 18 else 13,
            runtime = movie.runtime.toInt(),
            genres = movie.genres,
            actors = createCast(actors)
        )
    }

    private fun createCast(actors: List<Actor>?): List<Actor>{
        val actorList = ArrayList<Actor>()
        actors?.forEachIndexed { index, element ->
            if (index < 4){
                actorList.add(element)
            }
        }
        return actorList
    }

    companion object {
        private const val TAG = "myLogs"
    }
}