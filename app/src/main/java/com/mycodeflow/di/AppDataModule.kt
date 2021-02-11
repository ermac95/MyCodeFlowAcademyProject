package com.mycodeflow.di

import android.content.Context
import com.mycodeflow.repository.MovieDetailRepository
import com.mycodeflow.repository.MovieListRepository
import com.mycodeflow.viewmodels.MovieDetailsViewModel
import com.mycodeflow.viewmodels.MovieListViewModel
import com.mycodeflow.work.CacheUpdateWorkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDataModule {

    @Provides
    @Singleton
    fun provideMovieListViewModel(movieListRepository: MovieListRepository): MovieListViewModel {
        return MovieListViewModel(movieListRepository)
    }

    @Provides
    @Singleton
    fun provideWorkManager(context: Context): CacheUpdateWorkManager {
        return CacheUpdateWorkManager(context)
    }

    @Provides
    @Singleton
    fun provideDetailsViewModel(movieDetailRepository: MovieDetailRepository): MovieDetailsViewModel {
        return MovieDetailsViewModel(movieDetailRepository)
    }
}