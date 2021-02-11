package com.mycodeflow.di

import com.mycodeflow.api.TheMovieDBService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideMovieDataBaseService(): TheMovieDBService{
        return TheMovieDBService.createService()
    }
}