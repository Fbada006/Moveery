package com.disruption.moveery.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.toLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.disruption.moveery.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MovieDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieRoomDatabase: MovieRoomDatabase

    @Before
    fun setup() {
        movieRoomDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieRoomDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        movieRoomDatabase.close()
    }

    /**Ensure that the dao saves movie data and queries it properly*/
    @Test
    fun `insert movie from dao saves data`() = runBlockingTest {
        val movieList = DataFactory.makeMovieList(5)

        movieRoomDatabase.movieDao.insertAllMovies(movieList)

        val movies =
            movieRoomDatabase.movieDao.getAllMovies().toLiveData(20).getOrAwaitValue()

        assertThat(movies).isNotEmpty()
    }

    /**Ensure that the dao saves show data and queries it properly*/
    @Test
    fun `insert show from dao saves data`() = runBlockingTest {
        val showsList = DataFactory.makeShowList(5)

        movieRoomDatabase.movieDao.insertAllShows(showsList)

        val shows =
            movieRoomDatabase.movieDao.getAllShows().toLiveData(20).getOrAwaitValue()

        assertThat(shows).isNotEmpty()
    }

    /**Ensure that the dao returns an empty list when an empty list is inserted*/
    @Test
    fun `insert empty movie list from dao returns empty list`() = runBlockingTest {
        val movieList = DataFactory.makeMovieList(0)

        movieRoomDatabase.movieDao.insertAllMovies(movieList)

        val movies =
            movieRoomDatabase.movieDao.getAllMovies().toLiveData(20).getOrAwaitValue()

        assertThat(movies).isEmpty()
    }

    /**Ensure that the dao returns an empty list when an empty list is inserted*/
    @Test
    fun `insert empty show list from dao returns empty list`() = runBlockingTest {
        val showsList = DataFactory.makeShowList(0)

        movieRoomDatabase.movieDao.insertAllShows(showsList)

        val shows =
            movieRoomDatabase.movieDao.getAllShows().toLiveData(20).getOrAwaitValue()

        assertThat(shows).isEmpty()
    }
}