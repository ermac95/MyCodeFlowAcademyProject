package com.mycodeflow.ui

import android.app.Application
import com.mycodeflow.di.AppComponent
import com.mycodeflow.di.DaggerAppComponent


class MyApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger(){
        appComponent = DaggerAppComponent.factory()
            .create(applicationContext)
    }

}