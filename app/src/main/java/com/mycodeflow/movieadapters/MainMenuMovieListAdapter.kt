package com.mycodeflow.movieadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mycodeflow.academyproject.FragmentMoviesList
import com.mycodeflow.academyproject.R
import com.mycodeflow.datamodel.Movie

class MainMenuMovieListAdapter(
    private val movies: List<Movie>,
    private val clickListener: FragmentMoviesList.MovieDetailsListener?
): RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(movies[position])
        holder.itemView.setOnClickListener{
            clickListener?.showDetails(movies[position].id)
        }
    }

    override fun getItemCount(): Int = movies.size
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val bgImage: ImageView = itemView.findViewById(R.id.movie_item_bg_image)
    private val restrictionText: TextView = itemView.findViewById(R.id.movie_item_age_restriction_number)
    private val favorite: ImageView = itemView.findViewById(R.id.movie_item_favorite_indicator)
    private val tags: TextView = itemView.findViewById(R.id.movie_item_category_tags)
    private val rtFirstStar: ImageView = itemView.findViewById(R.id.movie_item_first_star)
    private val rtSecondStar: ImageView = itemView.findViewById(R.id.movie_item_second_star)
    private val rtThirdStar: ImageView = itemView.findViewById(R.id.movie_item_third_star)
    private val rtFourthStar: ImageView = itemView.findViewById(R.id.movie_item_fourth_star)
    private val rtFifthStar: ImageView = itemView.findViewById(R.id.movie_item_fifth_star)
    private val reviewText: TextView = itemView.findViewById(R.id.movie_item_review_text)
    private val mainTitle: TextView = itemView.findViewById(R.id.movie_item_title)
    private val durationTime: TextView = itemView.findViewById(R.id.movie_item_duration)

    fun onBind(movie: Movie){
        bgImage.setImageResource(movie.bgImage)
        restrictionText.text = movie.restrictionText
        if (movie.favorite)
            favorite.setImageResource(R.drawable.movie_favorite_indicator_on)
        else
            favorite.setImageResource(R.drawable.movie_favorite_indicator_off)
        tags.text = movie.tags
        //setting star icons
        val stars: List<ImageView> = listOf(rtFirstStar, rtSecondStar, rtThirdStar, rtFourthStar, rtFifthStar)
        setStarIcons(stars, movie)
        reviewText.text = movie.reviewText
        mainTitle.text = movie.mainTitle
        durationTime.text = movie.durationTime
    }

    private fun setStarIcons(stars: List<ImageView>, movie: Movie){
        for (index in stars.indices){
            if (index <= movie.rating - 1){
                stars[index].setImageResource(R.drawable.star_icon_on)
            }
            else {
                stars[index].setImageResource(R.drawable.star_icon_off)
            }
        }
    }
}
