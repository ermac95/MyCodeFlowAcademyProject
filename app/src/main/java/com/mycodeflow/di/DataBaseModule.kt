package com.mycodeflow.di

import android.content.Context
import com.mycodeflow.datasource.TheMovieDao
import com.mycodeflow.datasource.TheMovieDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(context: Context): TheMovieDataBase{
        return TheMovieDataBase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDataBase: TheMovieDataBase): TheMovieDao{
        return appDataBase.getMovieDao()
    }
}