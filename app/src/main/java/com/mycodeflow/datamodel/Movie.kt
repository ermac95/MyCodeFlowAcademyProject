package com.mycodeflow.datamodel

data class Movie(
    val id: Int,
    val bgImage: Int,
    val restrictionText: String,
    val favorite: Boolean,
    val tags: String,
    val rating: Int,
    val reviewText: String,
    val mainTitle: String,
    val durationTime: String,
    val detailsBg: Int,
    val detailDescription: String,
    val actors: List<Actor>
)