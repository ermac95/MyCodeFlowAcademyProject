package com.mycodeflow.academyproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class FragmentMoviesDetails : Fragment() {

    private var listener: BackToMenuListener? = null
    private var backToMenu : ImageView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)
        backToMenu = view.findViewById<ImageView>(R.id.movie_details_back_button)
            .apply {
                setOnClickListener{
                    listener?.backToMainMenu()
                }
            }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BackToMenuListener){
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface BackToMenuListener{
        fun backToMainMenu()
    }

}