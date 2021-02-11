package com.mycodeflow.datasource

import androidx.room.*
import com.mycodeflow.data.MovieDetailModel
import com.mycodeflow.data.MovieListItem

@Dao
interface TheMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieListItem>)

    @Query("SELECT * FROM MovieListItem")
    suspend fun getAllMoviesList(): List<MovieListItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieSelected(movie: MovieDetailModel)

    @Query("SELECT * FROM MovieDetailModel WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieDetailModel?

}