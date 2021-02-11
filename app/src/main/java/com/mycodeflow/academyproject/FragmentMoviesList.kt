package com.mycodeflow.academyproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mycodeflow.item.decorators.MovieListItemDecorator
import com.mycodeflow.moviesadapters.MainMenuMovieListAdapter
import com.mycodeflow.data.MovieListItem
import javax.inject.Inject
import com.mycodeflow.viewmodels.MovieListViewModel as MovieListViewModel

class FragmentMoviesList : Fragment() {

    @Inject lateinit var movieListViewModel: MovieListViewModel

    private var clickListener: MovieDetailsListener? = null
    private var movieListAdapter: MainMenuMovieListAdapter? = null
    private var rvMovieList: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.inject(this)
        Log.d("myLogs", "fragment started")
        if (context is MovieDetailsListener){
            clickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        rvMovieList = view.findViewById(R.id.rv_main_movie_list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMoviesAdapter()
        movieListViewModel.moviesList.observe(this.viewLifecycleOwner, this::updateData)
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
        movieListAdapter = null
        rvMovieList = null
    }

    private fun setupMoviesAdapter(){
        movieListAdapter = MainMenuMovieListAdapter(clickListener)
        rvMovieList?.apply {
            adapter = movieListAdapter
            rvMovieList?.addItemDecoration(MovieListItemDecorator(requireContext(),12, 12))
        }
    }

    private fun updateData(movies: List<MovieListItem>) {
        movieListAdapter?.setData(movies)
        Log.d("myLogs", "movieList = $movies")
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
