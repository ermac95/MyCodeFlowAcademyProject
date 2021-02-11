package com.mycodeflow.api

import com.mycodeflow.data.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDBService {

    //functionality of the service
    @GET("genre/movie/list")
    suspend fun loadGenreList(): GenreResponse

    @GET("movie/popular")
    suspend fun loadMoviesList(): PopularMovieResponse

    @GET("movie/{movie_id}")
    suspend fun loadMovieById(
        @Path("movie_id") movieId: Int
    ): MovieDetailsResponse

    @GET("configuration")
    suspend fun loadBaseImageConfig(): ImageConfigResponse

    @GET("movie/{movie_id}/credits")
    suspend fun loadMovieCast(
        @Path("movie_id") movieId: Int
    ): MovieCreditsResponse


    companion object {
        private const val API_KEY = "566541f3f88231e75a833da92d0815bd"
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        //getting an instance of the service
        fun createService(): TheMovieDBService{
            //initializing interceptor for adding api-key in all the other future requests
            val requestInterceptor = Interceptor {chain->
                val newUrl = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            }
            //initializing an OkHttpClient for further use in retrofit building
            val webClient = OkHttpClient().newBuilder()
                .addInterceptor(requestInterceptor)
                .build()

            //building retrofit
            return Retrofit.Builder()
                .client(webClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheMovieDBService::class.java)
        }
    }

}