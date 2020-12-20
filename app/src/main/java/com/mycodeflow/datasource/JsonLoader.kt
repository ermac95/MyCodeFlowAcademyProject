package com.mycodeflow.datasource

import android.content.Context
import android.util.Log
import com.mycodeflow.data.loadMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JsonLoader(private val context: Context) {

    suspend fun loadMoviesAsync() = withContext(Dispatchers.IO){
        Log.d("myLogs", "Load movies is in process")
        loadMovies(context)
    }
}
