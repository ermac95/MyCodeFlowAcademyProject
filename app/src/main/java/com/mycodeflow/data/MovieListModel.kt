package com.mycodeflow.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mycodeflow.converters.MovieTypeConverter

@Entity
data class MovieListModel(
        @PrimaryKey
        val id: Int,
        val title: String,
        val overview: String,
        val poster: String,
        val ratings: Double,
        val numberOfRatings: Int,
        val minimumAge: Int,
        @TypeConverters(MovieTypeConverter::class)
        val genres: List<Genre>,
        val release: String
)