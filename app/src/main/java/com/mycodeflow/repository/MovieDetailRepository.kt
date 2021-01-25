package com.mycodeflow.repository

import com.mycodeflow.data.*
import com.mycodeflow.datasource.MovieInteractor
import com.mycodeflow.datasource.TheMovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailRepository(
    private val movieRemoteSource: MovieInteractor,
    private val movieLocalSource: TheMovieDao
) {
    suspend fun getMovieById(movieId: Int): MovieDetailModel{
        val movie = getMovieFromDb(movieId)
        return if (movie != null){
            movie
        } else {
            val movieDetail = loadMovieFromWeb(movieId)
            movieLocalSource.insertMovieSelected(movieDetail)
            movieDetail
        }
    }

    private suspend fun getMovieFromDb(movieId: Int): MovieDetailModel? = withContext(Dispatchers.IO){
        movieLocalSource.getMovieById(movieId)
    }

    private suspend fun loadMovieFromWeb(movieId: Int): MovieDetailModel = withContext(Dispatchers.IO){
        val baseUrl: String = createImageBaseUrl()
        val cast: List<Actor> = loadCast(movieId, baseUrl)
        val newMovie = loadMovieSelected(movieId)
        val selectedMovie = composeMovie(baseUrl, cast, newMovie)
        selectedMovie
    }

    private suspend fun createImageBaseUrl(): String = withContext(Dispatchers.IO){
        val imageConfig = movieRemoteSource.loadImageConfig()
        val imageBaseUrl = imageConfig.images.secureBaseURL
        imageBaseUrl
    }

    private suspend fun loadCast(movieId: Int, baseUrl: String): List<Actor> = withContext(Dispatchers.IO){
        val actorsData = movieRemoteSource.loadMovieCast(movieId)
        val actorsList = actorsData.cast
        val actors = convertWebCastToModel(actorsList, baseUrl)
        actors
    }

    private fun convertWebCastToModel(actorData: List<Cast>, baseUrl: String): List<Actor>{
        return actorData.map { Actor(id = it.id.toInt(), name = it.name, profilePicture = "${baseUrl}w200${it.profilePath}") }
    }

    private suspend fun loadMovieSelected(movieId: Int) = withContext(Dispatchers.IO){
        val newMovie = movieRemoteSource.loadMovieById(movieId)
        newMovie
    }

    private fun composeMovie(
        baseUrl: String,
        cast: List<Actor>,
        movie: MovieDetailsResponse
    ): MovieDetailModel {
        return MovieDetailModel(
            id = movie.id.toInt(),
            title = movie.originalTitle,
            overview = movie.overview,
            backdrop = "${baseUrl}w200${movie.backdropPath}",
            ratings = movie.voteAverage,
            numberOfRatings = movie.voteCount.toInt(),
            minimumAge = if (movie.adult) 18 else 13,
            runtime = movie.runtime.toInt(),
            genres = movie.genres,
            actors = createCast(cast)
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
}