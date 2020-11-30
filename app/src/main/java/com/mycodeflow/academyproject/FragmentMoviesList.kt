package com.mycodeflow.academyproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

class FragmentMoviesList : Fragment() {

    private var listener : DetailsListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DetailsListener){
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        view.findViewById<ConstraintLayout>(R.id.movie_item_parent)?.apply{
            setOnClickListener{
                listener?.showDetails()
            }
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface DetailsListener{
        fun showDetails()
    }
}
