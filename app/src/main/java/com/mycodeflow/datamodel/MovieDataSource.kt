package com.mycodeflow.datamodel

import com.mycodeflow.academyproject.R
import java.lang.Exception

class MovieDataSource {
    fun getMovies(): List<Movie>{
        return listOf(
            Movie(1,
                R.drawable.movie_list_avengers_bg,
                "13+",
                false,
                "Action, Adventure, Drama",
                4,
                "125 REVIEWS",
                "Avengers:\nEnd Game",
                "137 MIN",
                R.drawable.movie_details_bg_avengers,
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe.",
                ActorDataSource().getActorsCastById(1)
            ),
            Movie(2,
                R.drawable.movie_list_tenet_bg,
                "16+",
                true,
                "Action, Sci-Fi, Thriller",
                5,
                "98 REVIEWS",
                "Tenet",
                "97 MIN",
                R.drawable.movie_details_bg_tenet,
                "An unnamed CIA agent, the \"Protagonist\", participates in an undercover operation at a Kyiv opera house. He is aided by a masked soldier with a distinctive trinket, who appears to \"un-fire\" a bullet through a hostile gunman.",
                ActorDataSource().getActorsCastById(2)
            ),
            Movie(3,
                R.drawable.movie_list_blackwidow_bg,
                "13+",
                false,
                "Action, Adventure, Sci-Fi",
                4,
                "38 REVIEWS",
                "Black Widow",
                "102 MIN",
                R.drawable.movie_details_bg_widow,
                "Natasha Romanoff finds herself alone and forced to confront a dangerous conspiracy with ties to her past. Pursued by a force that will stop at nothing to bring her down, Romanoff must deal with her history as a spy and the broken relationships left in her wake long before she became an Avenger.",
                ActorDataSource().getActorsCastById(1)
            ),
            Movie(4,
                R.drawable.movie_list_wonderwoman_bg,
                "13+",
                false,
                "Action, Adventure, Fantasy",
                5,
                "74 REVIEWS",
                "Wonder Woman 1984",
                "120 MIN",
                R.drawable.movie_details_bg_wonder,
                "In present-day Paris, Diana Prince receives a photographic plate from Wayne Enterprises (accompanied by a letter from Bruce Wayne) of herself and four men taken during World War I, prompting her to recall her past.",
                ActorDataSource().getActorsCastById(1)
            )
        )
    }

    fun getMovieById(movieId: Int): Movie{
        return when(movieId){
            1 -> getMovies()[0]
            2 -> getMovies()[1]
            3 -> getMovies()[2]
            4 -> getMovies()[3]
                else -> throw Exception()
        }
    }
}