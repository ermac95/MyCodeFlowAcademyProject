package com.mycodeflow.moviesadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mycodeflow.academyproject.FragmentMoviesList
import com.mycodeflow.academyproject.R
import com.mycodeflow.data.MovieListModel

class MainMenuMovieListAdapter(
    private val clickListener: FragmentMoviesList.MovieDetailsListener?
): RecyclerView.Adapter<MovieViewHolder>() {

    private var movies: List<MovieListModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(movies[position])
        holder.itemView.setOnClickListener{
            movies[position].id.let { it1 -> clickListener?.showDetails(it1) }
        }
    }

    override fun getItemCount(): Int = movies.size

    fun setData(updatedMovies: List<MovieListModel>){
        movies = updatedMovies
        notifyDataSetChanged()
    }
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val bgImage: ImageView = itemView.findViewById(R.id.movie_item_bg_image)
    private val minimumAge: TextView = itemView.findViewById(R.id.movie_item_age_restriction_number)
    private val favorite: ImageView = itemView.findViewById(R.id.movie_item_favorite_indicator)
    private val genres: TextView = itemView.findViewById(R.id.movie_item_category_tags)
    private val rtFirstStar: ImageView = itemView.findViewById(R.id.movie_item_first_star)
    private val rtSecondStar: ImageView = itemView.findViewById(R.id.movie_item_second_star)
    private val rtThirdStar: ImageView = itemView.findViewById(R.id.movie_item_third_star)
    private val rtFourthStar: ImageView = itemView.findViewById(R.id.movie_item_fourth_star)
    private val rtFifthStar: ImageView = itemView.findViewById(R.id.movie_item_fifth_star)
    private val reviewText: TextView = itemView.findViewById(R.id.movie_item_review_text)
    private val mainTitle: TextView = itemView.findViewById(R.id.movie_item_title)
    private val releaseDate: TextView = itemView.findViewById(R.id.movie_release_date)

    fun onBind(movie: MovieListModel?){
        //backdrop image
        val posterUrl: String? = movie?.poster
        Glide.with(itemView.context)
            .load(posterUrl)
            .placeholder(R.drawable.movie_list_avengers_bg)
            .into(bgImage)
        //minimum age text
        val ageNumber = movie?.minimumAge
        val ageText = itemView.context.getString(R.string.movie_minimum_age, ageNumber)
        minimumAge.text = ageText
        //favorite indicator
        favorite.setImageResource(R.drawable.movie_favorite_indicator_off)
        //genres tags
        val genreTags: String? = movie?.genres?.joinToString(separator = ", ") { it.name }
        genres.text = genreTags
        //setting star icons
        val stars: List<ImageView> = listOf(rtFirstStar, rtSecondStar, rtThirdStar, rtFourthStar, rtFifthStar)
        if (movie != null) {
            setStarIcons(stars, movie)
        }
        //reviews
        val reviews = itemView.context.getString(R.string.movie_review_text, movie?.numberOfRatings)
        reviewText.text = reviews
        //movie title
        mainTitle.text = movie?.title
        //movie duration
        releaseDate.text = movie?.release
    }

    private fun setStarIcons(stars: List<ImageView>, movie: MovieListModel){
        for (index in stars.indices){
            if (index <= (movie.ratings/2) - 0.5){
                stars[index].setImageResource(R.drawable.star_icon_on)
            }
            else {
                stars[index].setImageResource(R.drawable.star_icon_off)
            }
        }
    }
}
