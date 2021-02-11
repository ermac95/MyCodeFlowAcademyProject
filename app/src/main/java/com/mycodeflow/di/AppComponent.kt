package com.mycodeflow.di

import android.content.Context
import com.mycodeflow.academyproject.FragmentMoviesDetails
import com.mycodeflow.academyproject.FragmentMoviesList
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppDataModule::class, NetworkModule::class, DataBaseModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragmentList: FragmentMoviesList)

    fun inject(fragmentDetails: FragmentMoviesDetails)
}