package com.mycodeflow.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import com.mycodeflow.item.decorators.MovieListItemDecorator
import com.mycodeflow.adapters.MainMenuMovieListAdapter
import com.mycodeflow.work.CacheUpdateWorkManager
import javax.inject.Inject
import com.mycodeflow.viewmodels.MovieListViewModel as MovieListViewModel

class FragmentMoviesList : Fragment() {

    @Inject lateinit var movieListViewModel: MovieListViewModel
    @Inject lateinit var workManager: CacheUpdateWorkManager

    private var clickListener: MovieDetailsListener? = null
    private var movieListAdapter: MainMenuMovieListAdapter? = null
    private var rvMovieList: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.inject(this)
        if (context is MovieDetailsListener){
            clickListener = context
        }
        startBgCacheUpdate()
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
        //postponing transition until all data is loaded
        postponeEnterTransition()
        setupMoviesAdapter()
        movieListViewModel.moviesList.observe(this.viewLifecycleOwner) {
            movieListAdapter?.setData(it)
            //start transition since all the views have been measured and laid out
            (view.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.fragments_transition_duration).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.fragments_transition_duration).toLong()
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
        movieListAdapter = null
        rvMovieList = null
    }

    private fun startBgCacheUpdate(){
        workManager.startBackgroundWork()
    }

    private fun setupMoviesAdapter(){
        movieListAdapter = MainMenuMovieListAdapter(clickListener)
        rvMovieList?.apply {
            adapter = movieListAdapter
            rvMovieList?.addItemDecoration(MovieListItemDecorator(requireContext(),12, 12))
        }
    }

    interface MovieDetailsListener{
        fun showTransitionDetails(movieId: Int, view: View)
    }

    companion object {
        fun newInstance(): FragmentMoviesList {
            return FragmentMoviesList()
        }
    }
}
