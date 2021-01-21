package com.mycodeflow.academyproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycodeflow.item.decorators.MovieListItemDecorator
import com.mycodeflow.moviesadapters.MainMenuMovieListAdapter
import androidx.lifecycle.ViewModelProviders
import com.mycodeflow.data.Movie
import com.mycodeflow.viewmodels.MovieListViewModel as MovieListViewModel

class FragmentMoviesList : BaseFragment() {

    private var clickListener: MovieDetailsListener? = null
    private var movieListAdapter: MainMenuMovieListAdapter? = null
    private var rvMovieList: RecyclerView? = null

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
        rvMovieList = view.findViewById(R.id.rv_main_movie_list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMoviesAdapter()
        //creating viewModel
        val movieListFactory = dataProvider?.getFactory()
        val movieListViewModel = ViewModelProviders.of(this, movieListFactory)
            .get(MovieListViewModel::class.java)
        //setting data observers
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

    private fun updateData(movies: List<Movie>?) {
        movieListAdapter?.setData(movies)
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
