package com.mycodeflow.datamodel

import com.mycodeflow.academyproject.R
import java.lang.Exception

class ActorDataSource {
    private fun getActors(): List<List<Actor>> {
        return listOf(
            listOf(
                Actor(R.drawable.avengers_robert_djr, "Robert Downey Jr."),
                Actor(R.drawable.avengers_chris_evans, "Chris\nEvans"),
                Actor(R.drawable.avengers_mark_ruffalo, "Mark\nRuffalo"),
                Actor(R.drawable.avengers_chris_h, "Chris Hemsworth")
            ),
            listOf(
                Actor(R.drawable.tenet_first_actor, "John David Washington"),
                Actor(R.drawable.tenet_second_actor, "Robert Pattinson"),
                Actor(R.drawable.tenet_third_actor, "Elizabeth Debicki"),
                Actor(R.drawable.tenet_fourth_actor, "Kenneth Branagh")
            ),
            listOf(
                Actor(R.drawable.widow_first_actor, "Scarlett Johansson"),
                Actor(R.drawable.widow_second_actor, "Florence Pugh"),
                Actor(R.drawable.widow_third_actor, "David Harbour"),
                Actor(R.drawable.widow_fourth_actor, "O.T. Fagbenle")
            ),
            listOf(
                Actor(R.drawable.woman_first_actor, "Gal Gadot"),
                Actor(R.drawable.woman_second_actor, "Chris Pine"),
                Actor(R.drawable.woman_third_actor, "Kristen Wiig"),
                Actor(R.drawable.woman_fourth_actor, "Pedro Pascal")
            )
        )
    }

    fun getActorsCastById(movieId: Int?): List<Actor>{
        return when(movieId){
            1 -> getActors()[0]
            2 -> getActors()[1]
            3 -> getActors()[2]
            4 -> getActors()[3]
            else -> throw Exception()
        }
    }
}