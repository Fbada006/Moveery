package com.disruption.moveery.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.disruption.moveery.network.Movie

/**The database that the app uses*/
@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(context: Context): MovieRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieRoomDatabase::class.java,
                        "movies_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}