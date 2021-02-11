package com.mycodeflow.di

import com.mycodeflow.repository.MovieDetailRepository
import com.mycodeflow.repository.MovieListRepository
import com.mycodeflow.viewmodels.MovieDetailsViewModel
import com.mycodeflow.viewmodels.MovieListViewModel
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
    fun provideDetailsViewModel(movieDetailRepository: MovieDetailRepository): MovieDetailsViewModel {
        return MovieDetailsViewModel(movieDetailRepository)
    }
}