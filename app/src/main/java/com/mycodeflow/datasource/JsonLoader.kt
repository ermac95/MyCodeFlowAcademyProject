package com.mycodeflow.datasource

import android.content.Context
import android.util.Log
import com.mycodeflow.academyproject.MyApp
import com.mycodeflow.data.loadMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JsonLoader(myApp: MyApp) {

    private val context: Context = myApp

    suspend fun loadMoviesAsync() = withContext(Dispatchers.IO){
        Log.d("myLogs", "Load movies is in process")
        loadMovies(context)
    }
}
