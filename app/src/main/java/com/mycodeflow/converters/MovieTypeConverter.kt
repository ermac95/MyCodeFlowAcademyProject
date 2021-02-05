package com.mycodeflow.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mycodeflow.data.Actor
import com.mycodeflow.data.Genre

internal class MovieTypeConverter {

    @TypeConverter
    fun genresToJson(genres: List<Genre>): String{
        val genreTypeToken = object : TypeToken<ArrayList<Genre>>(){}.type
        return Gson().toJson(genres, genreTypeToken)
    }

    @TypeConverter
    fun genresFromJson(genreString: String): List<Genre> {
        val genreTypeToken = object : TypeToken<List<Genre>>(){}.type
        return Gson().fromJson(genreString, genreTypeToken)
    }

    @TypeConverter
    fun actorsToJson(actors: List<Actor>): String{
        val actorTypeToken = object : TypeToken<ArrayList<Actor>>(){}.type
        return Gson().toJson(actors, actorTypeToken)
    }

    @TypeConverter
    fun actorsFromJson(actorsString: String): List<Actor> {
        val actorTypeToken = object : TypeToken<List<Actor>>(){}.type
        return Gson().fromJson(actorsString, actorTypeToken)
    }
}