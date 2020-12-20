package com.mycodeflow.academyproject

import android.content.Context
import android.os.Bundle
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
    private lateinit var rvMovieList: RecyclerView

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
        movieListAdapter = MainMenuMovieListAdapter(movies, clickListener)
        rvMovieList = view.findViewById<RecyclerView>(R.id.rv_main_movie_list)
        .apply{
            adapter = movieListAdapter
            addItemDecoration(MovieListItemDecorator(requireContext(),12, 12))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataSource = dataProvider?.dataSource()
        scope.launch {
            movies = getMovies()
            updateData(movies)
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
        dataSource = null
    }

    private suspend fun getMovies(): List<Movie> {
        return dataSource?.getMoviesAsync() ?: emptyList()
    }

    private suspend fun updateData(movies: List<Movie>?) = withContext(Dispatchers.Main){
        movies?.let { movieListAdapter?.setData(it) }
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
