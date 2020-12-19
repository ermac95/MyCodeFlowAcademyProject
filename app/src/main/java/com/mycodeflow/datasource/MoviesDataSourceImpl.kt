package com.mycodeflow.datasource

import com.mycodeflow.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MoviesDataSource {
    suspend fun getMoviesAsync(): List<Movie>
    suspend fun getMovieByIdAsync(movieId: Int): Movie?
}

class MoviesDataSourceImpl(private val jsonLoader: JsonLoader) : MoviesDataSource {

    override suspend fun getMoviesAsync(): List<Movie> =
        withContext(Dispatchers.IO) {
            jsonLoader.loadMoviesAsync().toMutableList()
        }

    override suspend fun getMovieByIdAsync(movieId: Int): Movie? =
        withContext(Dispatchers.IO) {
            jsonLoader.loadMoviesAsync().toMutableList().find { movieId == it.id }
        }
}