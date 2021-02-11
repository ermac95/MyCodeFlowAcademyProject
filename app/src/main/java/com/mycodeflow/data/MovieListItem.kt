package com.mycodeflow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieListItem(
        @PrimaryKey
        val id: Int,
        val title: String,
        val overview: String,
        val poster: String,
        val ratings: Double,
        val numberOfRatings: Int,
        val minimumAge: Int,
        val genres: List<Genre>,
        val release: String
)