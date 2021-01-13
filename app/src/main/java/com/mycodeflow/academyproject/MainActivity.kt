package com.mycodeflow.academyproject

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity(), FragmentMoviesDetails.BackToMenuListener, FragmentMoviesList.MovieDetailsListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                    .add(R.id.main_frame_container, FragmentMoviesList.newInstance())
                    .addToBackStack(FragmentMoviesList.toString())
                    .commit()
        }
    }

    override fun showDetails(movieId: Int) {
        supportFragmentManager.beginTransaction()
                .add(R.id.main_frame_container, FragmentMoviesDetails.newInstance(movieId))
                .addToBackStack(FragmentMoviesDetails.toString())
                .commit()
    }

    override fun backToMainMenu() {
        supportFragmentManager.popBackStack()
    }
}
