package com.mycodeflow.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TheMovieDBClient {

    private const val API_KEY = "566541f3f88231e75a833da92d0815bd"
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    fun getClient(): MovieDataBaseService{
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
        return  Retrofit.Builder()
                .client(webClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieDataBaseService::class.java)
    }
}