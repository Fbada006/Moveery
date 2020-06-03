package com.disruption.moveery.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.disruption.moveery.models.favourites.movies.FavMovie
import com.disruption.moveery.models.favourites.shows.FavShow
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.utils.IntListConverter

/**The database that the app uses*/
@Database(
    entities = [Movie::class, TvShow::class, FavMovie::class, FavShow::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(IntListConverter::class)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}
