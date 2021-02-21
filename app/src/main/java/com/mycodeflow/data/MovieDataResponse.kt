package com.mycodeflow.data

import com.google.gson.annotations.SerializedName


//GenreResponse class with a list of genres
data class GenreResponse (
    val genres: List<GenreExample>
)

data class GenreExample (
    val id: Long,
    val name: String
)


//movie credits response
data class MovieCreditsResponse (
    val id: Long,
    val cast: List<Cast>,
    val crew: List<Cast>
)

data class Cast (
    val adult: Boolean,
    val gender: Long,
    val id: Long,

    @SerializedName("known_for_department")
    val knownForDepartment: Department,

    val name: String,

    @SerializedName("original_name")
    val originalName: String,

    val popularity: Double,

    @SerializedName("profile_path")
    val profilePath: String? = null,

    @SerializedName("cast_id")
    val castID: Long? = null,

    val character: String? = null,

    @SerializedName("credit_id")
    val creditID: String,

    val order: Long? = null,
    val department: Department? = null,
    val job: String? = null
)

enum class Department(val value: String) {
    Acting("Acting"),
    Art("Art"),
    Camera("Camera"),
    CostumeMakeUp("Costume & Make-Up"),
    Crew("Crew"),
    Directing("Directing"),
    Editing("Editing"),
    Lighting("Lighting"),
    Production("Production"),
    Sound("Sound"),
    VisualEffects("Visual Effects"),
    Writing("Writing");
}

//image config data classes
data class ImageConfigResponse (
    val images: ImageConfig,
    @SerializedName("change_keys")
    val changeKeys: List<String>
)

data class ImageConfig (
    @SerializedName("base_url")
    val baseURL: String,
    @SerializedName("secure_base_url")
    val secureBaseURL: String,
    @SerializedName("backdrop_sizes")
    val backdropSizes: List<String>,
    @SerializedName("logo_sizes")
    val logoSizes: List<String>,
    @SerializedName("poster_sizes")
    val posterSizes: List<String>,
    @SerializedName("profile_sizes")
    val profileSizes: List<String>,
    @SerializedName("still_sizes")
    val stillSizes: List<String>
)
//PopularMovie response data classes
data class PopularMovieResponse (
    val page: Long,
    val results: List<MovieListResponse>,

    @SerializedName("total_results")
    val totalResults: Long,

    @SerializedName("total_pages")
    val totalPages: Long
)


data class MovieListResponse (
    @SerializedName("poster_path")
    val posterPath: String,
    val adult: Boolean,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("genre_ids")
    val genreIds: List<Long>,
    val id: Long,
    @SerializedName("original_title")
    val originalTitle: String,
    val title: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val popularity: Double,
    @SerializedName("vote_count")
    val voteCount: Long,
    @SerializedName("vote_average")
    val voteAverage: Double
)

data class MovieDetailsResponse (
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,
    @SerializedName("imdb_id")
    val imdbID: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: Any? = null,
    val runtime: Long,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Long
)