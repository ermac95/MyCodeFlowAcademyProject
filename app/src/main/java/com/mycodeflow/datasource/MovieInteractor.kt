package com.mycodeflow.datasource

import com.mycodeflow.api.MovieDataBaseService
import com.mycodeflow.data.*

class MovieInteractor(private val service: MovieDataBaseService) {


    suspend fun loadGenres(): GenreResponse {
        return service.getGenreList()
    }

    suspend fun loadMovies(): PopularMovieResponse{
        return service.getMoviesList()
    }

    suspend fun loadMovieById(movieId: Int): MovieDetailsResponse{
        return service.getMovieById(movieId)
    }

    suspend fun loadImageConfig(): ImageConfigResponse {
        return service.getBaseImage()
    }

    suspend fun loadMovieCast(movieId: Int): MovieCreditsResponse {
        return service.getMovieCast(movieId)
    }
}