package com.disruption.moveery.mappers

import com.disruption.moveery.models.favourites.shows.FavShow
import com.disruption.moveery.models.shows.TvShow

/**Map a [TvShow] to a [FavShow]*/
fun TvShow.toFavShowDataModel() = FavShow(
    id = this.id,
    backdrop_path = this.backdrop_path,
    genre_ids = this.genre_ids,
    original_language = this.original_language,
    overview = this.overview,
    poster_path = this.poster_path,
    vote_average = this.vote_average,
    first_air_date = this.first_air_date,
    name = this.name
)

/**Map a [FavShow] to a [TvShow].*/
fun FavShow.toShowDomainModel() = TvShow(
    id = this.id,
    backdrop_path = this.backdrop_path,
    genre_ids = this.genre_ids,
    original_language = this.original_language,
    overview = this.overview,
    poster_path = this.poster_path,
    vote_average = this.vote_average,
    first_air_date = this.first_air_date,
    name = this.name,
    original_name = null,
    popularity = null,
    vote_count = null
)