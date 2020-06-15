package com.droidafricana.moveery.data

import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.models.shows.TvShow
import java.util.concurrent.ThreadLocalRandom

/**Provide fake data for testing*/
object DataFactory {

    /**Make fake movie list*/
    fun makeMovieList(count: Int): List<Movie> {
        val movies = mutableListOf<Movie>()
        repeat(count) {
            movies.add(makeMovie())
        }
        return movies
    }

    /**Make fake show list*/
    fun makeShowList(count: Int): List<TvShow> {
        val shows = mutableListOf<TvShow>()
        repeat(count) {
            shows.add(makeTvShow())
        }
        return shows
    }

    /**Make a fake movie*/
    private fun makeMovie(): Movie = Movie(
        id = randomInt(),
        isForAdult = randomBoolean(),
        backdrop_path = randomString(),
        genre_ids = randomList(),
        original_language = randomString(),
        original_title = randomString(),
        overview = randomString(),
        poster_path = randomString(),
        release_date = randomString(),
        title = randomString(),
        hasVideo = randomBoolean(),
        vote_average = randomDouble()
    )

    /**Make a fake TvShow*/
    private fun makeTvShow(): TvShow = TvShow(
        id = randomInt(),
        backdrop_path = randomString(),
        genre_ids = randomList(),
        original_language = randomString(),
        overview = randomString(),
        poster_path = randomString(),
        vote_average = randomDouble(),
        first_air_date = randomString(),
        name = randomString(),
        original_name = randomString(),
        vote_count = randomInt(),
        popularity = randomDouble()
    )

    /**Generate random double*/
    private fun randomDouble(): Double = randomInt().toDouble()

    /**Generate random string*/
    private fun randomString(): String = java.util.UUID.randomUUID().toString()

    /**Generate random int*/
    private fun randomInt(): Int = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

    /**Generate random boolean*/
    private fun randomBoolean(): Boolean = Math.random() < 0.5

    /**Generate random list*/
    private fun randomList(): List<Int> {
        val list = mutableListOf<Int>()
        repeat(5) {
            list.add(randomInt())
        }
        return list
    }
}