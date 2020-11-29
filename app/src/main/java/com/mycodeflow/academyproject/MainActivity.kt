package com.mycodeflow.academyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

class MainActivity : AppCompatActivity(), FragmentMoviesList.DetailsListener, FragmentMoviesDetails.BackToMenuListener  {

    private val listFragment = FragmentMoviesList()
    private var detailsFragment = FragmentMoviesDetails()
    private var firstLaunch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                    .apply {
                        add(R.id.main_frame_container, listFragment)
                        addToBackStack(listFragment.toString())
                        commit()
                    }
        }
    }

    override fun showDetails() {
        supportFragmentManager.beginTransaction()
                .apply {
                    add(R.id.main_frame_container, detailsFragment, "detailsFragment")
                    addToBackStack(detailsFragment.toString())
                    commit()
                }
    }

    override fun backToMainMenu() {
        supportFragmentManager.beginTransaction()
                .apply {
                    replace(R.id.main_frame_container, listFragment)
                    addToBackStack(null)
                    commit()
                }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean("firstLaunch", firstLaunch)
    }

}