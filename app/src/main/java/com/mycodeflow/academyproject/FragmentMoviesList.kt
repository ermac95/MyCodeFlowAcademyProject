package com.mycodeflow.academyproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycodeflow.MovieListItemDecorator
import com.mycodeflow.data.Movie
import com.mycodeflow.datasource.MoviesDataSource
import com.mycodeflow.moviesadapters.MainMenuMovieListAdapter
import kotlinx.coroutines.*

class FragmentMoviesList : BaseFragment() {

    private var clickListener: MovieDetailsListener? = null
    private var dataSource: MoviesDataSource? = null
    private var scope = CoroutineScope(Dispatchers.IO)
    private var movies: List<Movie>? = null
    private var movieListAdapter: MainMenuMovieListAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieDetailsListener){
            clickListener = context
        }
        dataSource = dataProvider?.dataSource()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val rvMovieList = view.findViewById<RecyclerView>(R.id.rv_main_movie_list)
        scope.launch {
            movies = getMovies()
            movieListAdapter = MainMenuMovieListAdapter(movies!!, clickListener)
            rvMovieList.adapter = movieListAdapter
            rvMovieList.addItemDecoration(MovieListItemDecorator(requireContext(),12, 12))
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
        dataSource = null
    }

    private suspend fun getMovies(): List<Movie> {
        return dataSource?.getMoviesAsync()!!
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
