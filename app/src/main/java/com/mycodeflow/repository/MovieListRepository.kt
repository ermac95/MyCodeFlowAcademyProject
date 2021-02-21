package com.mycodeflow.repository

import android.util.Log
import com.mycodeflow.api.TheMovieDBService
import com.mycodeflow.data.*
import com.mycodeflow.datasource.TheMovieDao
import com.mycodeflow.utils.MovieUpdateNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieListRepository @Inject constructor(
    val movieRemoteSource: TheMovieDBService,
    val movieLocalSource: TheMovieDao
){
    //response for viewModels movie list request
    suspend fun getMovies(forceRefresh: Boolean): List<MovieListItem>{
        //trying to get moviesList from Apps DataBase
        val data = getMoviesFromDb()
        return if (!forceRefresh && data.isNotEmpty()){
            data
        } else {
            //getting moviesList from Web
            val movies = getMoviesFromWeb()
            movieLocalSource.insertAllMovies(movies)
            return movies
        }
    }

    //taking movie data from local cache
    private suspend fun getMoviesFromDb(): List<MovieListItem> = withContext(Dispatchers.IO){
        movieLocalSource.getAllMoviesList()
    }

    //loading movies from web and updating cache
    private suspend fun getMoviesFromWeb(): List<MovieListItem> = withContext(Dispatchers.IO){
        //making movie model
        val baseUrl = createImageBaseUrl()
        val genresData = loadGenresFromWeb()
        val moviesData = loadMoviesOnline()
        convertWebMoviesToModel(baseUrl, genresData, moviesData)
    }

    //creating base image url for image loading
    private suspend fun createImageBaseUrl(): String = withContext(Dispatchers.IO){
        val imageConfig = movieRemoteSource.loadBaseImageConfig()
        imageConfig.images.secureBaseURL
    }

    //loading genres list from web
    private suspend fun loadGenresFromWeb() = withContext(Dispatchers.IO){
        val genreResponse = movieRemoteSource.loadGenreList()
        val genreList = genreResponse.genres
        convertWebGenresToModel(genreList)
    }

    //converter response genres to model genres
    private fun convertWebGenresToModel(genreData: List<GenreExample>): List<Genre>{
        return genreData.map { Genre(id = it.id, name = it.name) }
    }

    //loading movie list from web and converting to model
    private suspend fun loadMoviesOnline(): List<MovieListResponse> = withContext(Dispatchers.IO){
        val moviesData = movieRemoteSource.loadMoviesList()
        moviesData.results
    }

    private fun convertWebMoviesToModel(
        baseUrl: String,
        genres: List<Genre>,
        moviesData: List<MovieListResponse>,
    ): List<MovieListItem>{
        val genresMap = genres.associateBy { it.id }
        return moviesData.map { MovieResponse ->
            (MovieListItem(
                id = MovieResponse.id.toInt(),
                title = MovieResponse.originalTitle,
                overview = MovieResponse.overview,
                poster = "${baseUrl}w400${MovieResponse.posterPath}",
                ratings = MovieResponse.voteAverage,
                numberOfRatings = MovieResponse.voteCount.toInt(),
                minimumAge = if (MovieResponse.adult) 18 else 13,
                genres = composeGenresList(genresMap, MovieResponse.genreIds),
                release = MovieResponse.releaseDate
            ))
        }
    }

    private fun composeGenresList(genreMap: Map<Long, Genre>, idList: List<Long>): List<Genre>{
        val bufferedList: ArrayList<Genre> = ArrayList()
        idList.forEach{
            if (genreMap.containsKey(it)){
                genreMap[it]?.let {it1 -> bufferedList.add(it1)}
            }
        }
        return bufferedList
    }
}
