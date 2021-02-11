package com.mycodeflow.datasource

import android.content.Context
import androidx.room.*
import com.mycodeflow.converters.MovieTypeConverter
import com.mycodeflow.data.MovieDetailModel
import com.mycodeflow.data.MovieListItem

@Database(entities = [MovieListItem::class, MovieDetailModel::class], version = 1)
@TypeConverters(MovieTypeConverter::class)
abstract class TheMovieDataBase: RoomDatabase() {

    abstract fun getMovieDao(): TheMovieDao

    companion object {

        fun getInstance(context: Context): TheMovieDataBase{
            return Room.databaseBuilder(
                context,
                TheMovieDataBase::class.java,
                "movie_database"
                )
                .build()
        }
    }
}