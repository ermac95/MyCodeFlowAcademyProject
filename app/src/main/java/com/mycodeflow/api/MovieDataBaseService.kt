package com.mycodeflow.api

import com.mycodeflow.data.*
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDataBaseService {

    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreResponse

    @GET("movie/popular")
    suspend fun getMoviesList(): PopularMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int
    ): MovieDetailsResponse

    @GET("configuration")
    suspend fun getBaseImage(): ImageConfigResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int
    ): MovieCreditsResponse

}