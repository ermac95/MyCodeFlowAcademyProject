package com.mycodeflow.movieadapters

import android.content.Context
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
    context: Context,
    private val movies: List<Movie>,
    private val clickListener: FragmentMoviesList.MovieDetailsListener?
): RecyclerView.Adapter<MovieViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private fun getItem(position: Int): Movie = movies[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(inflater.inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.itemView.setOnClickListener{
            clickListener?.showDetails(getItem(position).id)
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
        rtFirstStar.setImageResource(if(movie.rating>=1)R.drawable.star_icon_on else R.drawable.star_icon_off)
        rtSecondStar.setImageResource(if(movie.rating>=2)R.drawable.star_icon_on else R.drawable.star_icon_off)
        rtThirdStar.setImageResource(if(movie.rating>=3)R.drawable.star_icon_on else R.drawable.star_icon_off)
        rtFourthStar.setImageResource(if(movie.rating>=4)R.drawable.star_icon_on else R.drawable.star_icon_off)
        rtFifthStar.setImageResource(if(movie.rating==5)R.drawable.star_icon_on else R.drawable.star_icon_off)
        reviewText.text = movie.reviewText
        mainTitle.text = movie.mainTitle
        durationTime.text = movie.durationTime
    }
}