package com.droidafricana.moveery.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.droidafricana.moveery.models.favourites.movies.FavMovie
import com.droidafricana.moveery.models.favourites.shows.FavShow
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.utils.IntListConverter

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
