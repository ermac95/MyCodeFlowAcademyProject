package com.mycodeflow.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycodeflow.data.*
import com.mycodeflow.datasource.MovieInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(private val dataSource: MovieInteractor): ViewModel() {

    private var mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())
    val moviesList: LiveData<List<Movie>> get() = mutableMoviesList

    private var baseImageUrl: String? = null
    private var genre: List<Genre>? = null
    private var newMovieList: ArrayList<MovieDetailsResponse> = ArrayList()

    init {
        loadMovies()
    }

    private suspend fun loadGenres() = withContext(Dispatchers.IO){
        val genreResponse = dataSource.loadGenres()
        val genreList = genreResponse.genres
        val newGenreList = parseGenres(genreList)
        genre = newGenreList
    }

    private fun parseGenres(genreData: List<GenreExample>): List<Genre>{
        return genreData.map {Genre(id = it.id, name = it.name)}
    }

    private suspend fun createImageBaseUrl() = withContext(Dispatchers.IO){
        val imageConfig = dataSource.loadImageConfig()
        val imageBaseUrl = imageConfig.images.secureBaseURL
        baseImageUrl = imageBaseUrl
    }

    private fun loadMovies(){
        viewModelScope.launch {
            createImageBaseUrl()
            loadGenres()
            val moviesData = dataSource.loadMovies()
            val moviesListData = moviesData.results
            val moviesIds = parseMovieIdsList(moviesListData)
            Log.d(TAG, "moviesIds = $moviesIds")
            loadMoviesListByIds(moviesIds)
            Log.d(TAG, "moviesNewList = $newMovieList")
            val parsedMovieList = parseMoviesList(newMovieList, baseImageUrl)
            Log.d(TAG, "parsedMovies = $parsedMovieList")
            val updatedMoviesList = mutableMoviesList.value?.plus(parsedMovieList).orEmpty()
            mutableMoviesList.postValue(updatedMoviesList)
            //val newMoviesList = parseMovies(moviesListData, genre, baseImageUrl)
            //val updatedMoviesList = mutableMoviesList.value?.plus(newMoviesList).orEmpty()
            //mutableMoviesList.postValue(updatedMoviesList)
        }
    }

    private fun parseMovieIdsList(moviesList: List<MovieListResponse>): ArrayList<Long> {
        val moviesIdsList: ArrayList<Long> = ArrayList()
        moviesList.forEach{
            moviesIdsList.add(it.id)
        }
        return moviesIdsList
    }

    private suspend fun loadMoviesListByIds(idsList: ArrayList<Long>) = withContext(Dispatchers.IO){
        idsList.forEach{
            val newMovie = dataSource.loadMovieById(it.toInt())
            newMovieList.add(newMovie)
        }
    }

    private fun parseMoviesList(
        moviesData: List<MovieDetailsResponse>,
        baseImageUrl: String?
    ): List<Movie>{
        return moviesData.map { MovieResponse ->
            (Movie(
                id = MovieResponse.id.toInt(),
                title = MovieResponse.originalTitle,
                overview = MovieResponse.overview,
                poster = "${baseImageUrl}w400${MovieResponse.posterPath}",
                backdrop = "${baseImageUrl}w400${MovieResponse.backdropPath}",
                ratings = MovieResponse.voteAverage,
                numberOfRatings = MovieResponse.voteCount.toInt(),
                minimumAge = if (MovieResponse.adult) 18 else 13,
                runtime = MovieResponse.runtime.toInt(),
                genres = MovieResponse.genres,
                actors = null
            ))
        }
    }

    private fun parseMovies(
        moviesData: List<MovieListResponse>,
        genre: List<Genre>?,
        baseImageUrl: String?
    ): List<Movie>{
        val genresMap = genre?.associateBy { it.id }
        return moviesData.map { MovieResponse ->
            (Movie(
                id = MovieResponse.id.toInt(),
                title = MovieResponse.originalTitle,
                overview = MovieResponse.overview,
                poster = "${baseImageUrl}w400${MovieResponse.posterPath}",
                backdrop = "${baseImageUrl}w400${MovieResponse.backdropPath}",
                ratings = MovieResponse.voteAverage,
                numberOfRatings = MovieResponse.voteCount.toInt(),
                minimumAge = if (MovieResponse.adult) 18 else 13,
                runtime = 100,
                genres = attachGenresToMovie(genresMap, MovieResponse.genreIDS),
                actors = null
            ))
        }
    }

    private fun attachGenresToMovie(genreMap: Map<Long, Genre>?, idList: List<Long>): List<Genre>{
        val bufferedList: ArrayList<Genre> = ArrayList()
        idList.forEach {
            if (genreMap?.containsKey(it) == true) {
                genreMap[it]?.let { it1 -> bufferedList.add(it1) }
            }
        }
        return bufferedList
    }

    companion object {
        private const val TAG = "myLogs"
    }
}