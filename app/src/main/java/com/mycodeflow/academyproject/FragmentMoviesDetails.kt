package com.mycodeflow.academyproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mycodeflow.data.MovieDetailModel
import com.mycodeflow.item.decorators.ActorListItemDecorator
import com.mycodeflow.moviesadapters.DetailCastListAdapter
import com.mycodeflow.viewmodels.MovieDetailsViewModel


class FragmentMoviesDetails : BaseFragment() {

    private lateinit var backButton: ImageView
    private lateinit var backDrop: ImageView
    private lateinit var minimumAge: TextView
    private lateinit var title: TextView
    private lateinit var genresTags: TextView
    private lateinit var firstStar: ImageView
    private lateinit var secondStar: ImageView
    private lateinit var thirdStar: ImageView
    private lateinit var fourthStar: ImageView
    private lateinit var fifthStar: ImageView
    private lateinit var numberOfReviews: TextView
    private lateinit var storyLineTitle: TextView
    private lateinit var storyLine: TextView
    private lateinit var castTitle: TextView
    private lateinit var rvCastList: RecyclerView
    private var castListAdapter: DetailCastListAdapter? = null
    private var listener: BackToMenuListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BackToMenuListener){
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //inflating main view and setup views
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)
        setupViews(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //creating viewModel with factory
        val movieDetailsFactory = dataProvider?.getFactory()
        val movieDetailsViewModel = ViewModelProviders.of(this, movieDetailsFactory)
            .get(MovieDetailsViewModel::class.java)
        //getting movie by id
        val movieId = arguments?.getInt(KEY_MOVIE_ID, 0) ?: 1
        movieDetailsViewModel.updateMovieDetails(movieId)
        //observe changes in viewModels movie to get it later
        movieDetailsViewModel.movieExample.observe(this.viewLifecycleOwner){
            updateDetailsScreen(it)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        castListAdapter = null
    }

    private fun setupViews(view: View){
        backButton = view.findViewById<ImageView>(R.id.movie_details_back_button).apply {
            setOnClickListener{listener?.backToMainMenu()}
        }
        backDrop = view.findViewById(R.id.movie_details_bg)
        minimumAge = view.findViewById(R.id.movie_details_age_text)
        title = view.findViewById(R.id.movie_details_title)
        genresTags = view.findViewById(R.id.movie_details_tags)
        firstStar = view.findViewById(R.id.details_first_star)
        secondStar = view.findViewById(R.id.details_second_star)
        thirdStar = view.findViewById(R.id.details_third_star)
        fourthStar = view.findViewById(R.id.details_fourth_star)
        fifthStar = view.findViewById(R.id.details_fifth_star)
        numberOfReviews = view.findViewById(R.id.details_review_text)
        storyLine = view.findViewById(R.id.movie_details_storyline_text)
        storyLineTitle = view.findViewById(R.id.storyline_title)
        castTitle = view.findViewById(R.id.cast_title)
        rvCastList = view.findViewById(R.id.rv_details_cast)
    }

    private fun updateDetailsScreen(movie: MovieDetailModel) {
        //set background image
        Glide.with(requireView())
            .load(movie.backdrop)
            .placeholder(R.drawable.loading_bg)
            .into(backDrop)
        //age restriction
        val ageText = getString(R.string.movie_minimum_age, movie.minimumAge)
        minimumAge.text = ageText
        //title
        title.text = movie.title
        //genre tags
        val genreTags: String = movie.genres.joinToString(separator = ", ") { it.name }
        genresTags.text = genreTags
        //star icons
        val starsList: List<ImageView> = listOf(firstStar, secondStar, thirdStar, fourthStar, fifthStar)
        setStarIcons(starsList, movie)
        //number of reviews
        val reviews = getString(R.string.movie_review_text, movie.numberOfRatings)
        numberOfReviews.text = reviews
        //storyline
        storyLineTitle.text = getString(R.string.movie_details_storyline)
        storyLine.text = movie.overview
        //actor cast
        if (movie.actors?.isNotEmpty() == true){
            castTitle.text = getString(R.string.movie_details_cast)
            castListAdapter = DetailCastListAdapter(movie.actors)
            rvCastList.addItemDecoration(ActorListItemDecorator(requireContext(), 16))
            rvCastList.adapter = castListAdapter
        }
    }

    private fun setStarIcons(stars: List<ImageView>, movie: MovieDetailModel?) {
        if (movie!= null) {
            for (index in stars.indices){
                if (index <= movie.ratings) {
                    stars[index].setImageResource(R.drawable.star_icon_on)
                }
                else {
                    stars[index].setImageResource(R.drawable.star_icon_off)
                }
            }
        }
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
