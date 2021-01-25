package com.mycodeflow.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class MovieDetailModel(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val title: String,
        val overview: String,
        val backdrop: String,
        val ratings: Double,
        val numberOfRatings: Int,
        val minimumAge: Int,
        val runtime: Int?,
        val genres: List<Genre>,
        val actors: List<Actor>?,
)