package com.mycodeflow.academyproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycodeflow.MovieListItemDecorator
import com.mycodeflow.datamodel.MovieDataSource
import com.mycodeflow.movieadapters.MainMenuMovieListAdapter

class FragmentMoviesList : Fragment() {

    private var clickListener: MovieDetailsListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieDetailsListener){
            clickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val rvMovieList = view.findViewById<RecyclerView>(R.id.rv_main_movie_list)
        val movies = MovieDataSource().getMovies()
        val movieListAdapter = MainMenuMovieListAdapter(movies, clickListener)
        rvMovieList.adapter = movieListAdapter
        rvMovieList.addItemDecoration(MovieListItemDecorator(requireContext(),12, 11, 12))
        return view
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    interface MovieDetailsListener{
        fun showDetails(movieId: Int)
    }

    companion object {
        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }
    }
}
