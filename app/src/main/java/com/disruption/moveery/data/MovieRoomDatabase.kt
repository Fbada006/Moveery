package com.disruption.moveery.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.utils.IntListConverter

/**The database that the app uses*/
@Database(entities = [Movie::class, TvShow::class], version = 1, exportSchema = false)
@TypeConverters(IntListConverter::class)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

//    companion object {
//        private var INSTANCE: MovieRoomDatabase? = null
//
//        /**Returns a database instance if it does not exist*/
//        fun getDatabase(context: Context): MovieRoomDatabase {
//            synchronized(this) {
//                var instance =
//                    INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        MovieRoomDatabase::class.java,
//                        "movies_database"
//                    )
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
}