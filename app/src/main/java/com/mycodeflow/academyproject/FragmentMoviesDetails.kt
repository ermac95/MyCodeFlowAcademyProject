package com.mycodeflow.academyproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mycodeflow.ActorListItemDecorator
import com.mycodeflow.datamodel.ActorDataSource
import com.mycodeflow.datamodel.Movie
import com.mycodeflow.datamodel.MovieDataSource
import com.mycodeflow.movieadapters.DetailCastListAdapter


class FragmentMoviesDetails : Fragment() {

   private var listener: BackToMenuListener? = null
    private var movieId: Int? = null
    private var movie: Movie? = null
    private var rvCastList: RecyclerView? = null
    private var castListAdapter: DetailCastListAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BackToMenuListener){
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //getting arguments
        movieId = arguments?.getInt(KEY_MOVIE_ID, 0) ?: 1
        movie = MovieDataSource().getMovieById(movieId!!)
        //inflating main view
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)
        //setting clickListener on backButton
        view?.findViewById<ImageView>(R.id.movie_details_back_button)?.setOnClickListener{listener?.backToMainMenu()}
        //movie main bg
        view.findViewById<ImageView>(R.id.movie_details_bg).apply {
            movie?.detailsBg?.let { setImageResource(it) }
        }
        //movie age restriction
        view.findViewById<TextView>(R.id.movie_details_age_text).apply{
            text = movie?.restrictionText
        }
        //movie main title
        view.findViewById<TextView>(R.id.movie_details_title).apply {
            text = movie?.mainTitle
        }
        //movie tags
        view.findViewById<TextView>(R.id.movie_details_tags).apply{
            text = movie?.tags
        }
        //movie rating
        view.findViewById<ImageView>(R.id.details_first_star).apply {
            setImageResource(if(movie?.rating!!>=1)R.drawable.star_icon_on else R.drawable.star_icon_off)
        }
        view.findViewById<ImageView>(R.id.details_second_star).apply {
            setImageResource(if(movie?.rating!!>=2)R.drawable.star_icon_on else R.drawable.star_icon_off)
        }
        view.findViewById<ImageView>(R.id.details_third_star).apply {
            setImageResource(if(movie?.rating!!>=3)R.drawable.star_icon_on else R.drawable.star_icon_off)
        }
        view.findViewById<ImageView>(R.id.details_fourth_star).apply {
            setImageResource(if(movie?.rating!!>=4)R.drawable.star_icon_on else R.drawable.star_icon_off)
        }
        view.findViewById<ImageView>(R.id.details_fifth_star).apply {
            setImageResource(if(movie?.rating!!>=5)R.drawable.star_icon_on else R.drawable.star_icon_off)
        }
        //movie number of reviews
        view.findViewById<TextView>(R.id.details_review_text).apply {
            text = movie?.reviewText
        }
        //movie storyline text
        view.findViewById<TextView>(R.id.movie_details_storyline_text).apply {
            text = movie?.detailDescription
        }
        //setting recycler and its attributes
        val actors = ActorDataSource().getActorsCastById(movieId)
        castListAdapter = DetailCastListAdapter(requireContext(), actors)
        rvCastList = view.findViewById<RecyclerView>(R.id.rv_details_cast).apply {
            adapter = castListAdapter
            addItemDecoration(ActorListItemDecorator(requireContext(), 16))
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        castListAdapter = null
        rvCastList = null
    }

    interface BackToMenuListener{
        fun backToMainMenu()
    }

    companion object {
        private const val KEY_MOVIE_ID = "myMovieId"
        fun newInstance(movieId: Int): FragmentMoviesDetails{
            return FragmentMoviesDetails().apply {
                arguments = Bundle().apply {
                    putInt(KEY_MOVIE_ID, movieId)
                }
            }
        }
    }
}
