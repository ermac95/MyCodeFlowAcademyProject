package com.mycodeflow.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(), FragmentMoviesDetails.BackToMenuListener, FragmentMoviesList.MovieDetailsListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame_container, FragmentMoviesList.newInstance())
                    .addToBackStack(FragmentMoviesList.toString())
                    .commit()
            intent?.let(::handleIncomingIntent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null){
            handleIncomingIntent(intent)
        }
    }

    private fun handleIncomingIntent(intent: Intent){
        when(intent.action){
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toIntOrNull()
                if (id != null) {
                    showDetails(id)
                    NotificationManagerCompat.from(this).cancelAll()
                }
            }
        }
    }

    private fun showDetails(movieId: Int){
        supportFragmentManager.beginTransaction()
                .add(R.id.main_frame_container, FragmentMoviesDetails.newInstance(movieId))
                .addToBackStack(FragmentMoviesDetails.toString())
                .commit()
    }

    override fun showTransitionDetails(movieId: Int, view: View) {
        Log.d("myLogs", "This is transition with views")
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addSharedElement(view, resources.getString(R.string.movie_details_transition_name))
            replace(R.id.main_frame_container, FragmentMoviesDetails.newInstance(movieId))
            addToBackStack(null)
        }
    }

    override fun backToMainMenu() {
        supportFragmentManager.popBackStack()
    }
}
