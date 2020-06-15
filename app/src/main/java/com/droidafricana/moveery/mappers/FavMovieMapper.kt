package com.droidafricana.moveery.mappers

import com.droidafricana.moveery.models.favourites.movies.FavMovie
import com.droidafricana.moveery.models.movies.Movie

/**Map a [Movie] to a [FavMovie]*/
fun Movie.toFavMovieDataModel() = FavMovie(
    id = this.id,
    backdrop_path = this.backdrop_path,
    genre_ids = this.genre_ids,
    original_language = this.original_language,
    original_title = this.original_title,
    overview = this.overview,
    poster_path = this.poster_path,
    release_date = this.release_date,
    title = this.title,
    vote_average = this.vote_average
)

/**Map a [FavMovie] to a [Movie].*/
fun FavMovie.toMovieDomainModel() = Movie(
    id = this.id,
    backdrop_path = this.backdrop_path,
    genre_ids = this.genre_ids,
    original_language = this.original_language,
    original_title = this.original_title,
    overview = this.overview,
    poster_path = this.poster_path,
    release_date = this.release_date,
    title = this.title,
    vote_average = this.vote_average,
    hasVideo = null,
    isForAdult = null
)