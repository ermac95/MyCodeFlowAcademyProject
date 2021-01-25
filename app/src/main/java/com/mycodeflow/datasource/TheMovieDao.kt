package com.mycodeflow.datasource

import androidx.room.*
import com.mycodeflow.data.MovieDetailModel
import com.mycodeflow.data.MovieListModel

@Dao
interface TheMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieListModel>)

    @Query("SELECT * FROM MovieListModel")
    suspend fun getAllMoviesList(): List<MovieListModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieSelected(movie: MovieDetailModel)

    @Query("SELECT * FROM MovieDetailModel WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieDetailModel?

}