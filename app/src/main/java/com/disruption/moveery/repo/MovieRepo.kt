package com.disruption.moveery.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.disruption.moveery.data.MovieLocalCache
import com.disruption.moveery.data.movies.MovieBoundaryCallBack
import com.disruption.moveery.data.movies.search.SearchedMovieDataSource
import com.disruption.moveery.data.movies.similar.SimilarMovieDataSource
import com.disruption.moveery.data.shows.ShowBoundaryCallBack
import com.disruption.moveery.data.shows.search.SearchedShowDataSource
import com.disruption.moveery.data.shows.similar.SimilarShowDataSource
import com.disruption.moveery.models.movies.movie.Movie
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.utils.Constants.DATABASE_PAGE_SIZE
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * Repository class that works with local and remote data sources.
 * This class supplies data to the ViewModel to display
 */
@Suppress("KDocUnresolvedReference")
class MovieRepo @Inject constructor(
    private val movieLocalCache: MovieLocalCache,
    private val movieBoundaryCallBack: MovieBoundaryCallBack,
    private val showBoundaryCallBack: ShowBoundaryCallBack,
    private val scope: CoroutineScope
) {

    private val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .build()

    /**Get all the movies to from the local storage*/
    fun getAllMovies(): LiveData<PagedList<Movie>> {
        val dataSourceFactory = movieLocalCache.getMovieData()

        return LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(movieBoundaryCallBack)
            .build()
    }

    /**Get all the movies from the local storage*/
    fun getAllShows(): LiveData<PagedList<TvShow>> {
        val factory = movieLocalCache.getShowsData()

        return LivePagedListBuilder(factory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(showBoundaryCallBack)
            .build()
    }

    /**Returns the searched movie with paging involved*/
    fun getSearchedMovieList(
        queryLiveData: MutableLiveData<String>
    ): LiveData<PagedList<Movie>> {

        return Transformations.switchMap(queryLiveData) {
            initializeSearchMoviePagedListBuilder(it, scope).build()
        }
    }

    /**Returns the searched show with paging involved*/
    fun getSearchedShowList(
        queryLiveData: MutableLiveData<String>
    ): LiveData<PagedList<TvShow>> {

        return Transformations.switchMap(queryLiveData) {
            initializeSearchShowPagedListBuilder(it, scope).build()
        }
    }

    private fun initializeSearchShowPagedListBuilder(
        query: String,
        scope: CoroutineScope
    ): LivePagedListBuilder<Int, TvShow> {
        val dataSourceFactory = object : DataSource.Factory<Int, TvShow>() {
            override fun create(): DataSource<Int, TvShow> {
                return SearchedShowDataSource(scope, query)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    /**Returns similar movies with paging to the [MovieDetailsFragment]*/
    fun getSimilarMovieList(
        movieIdLiveData: MutableLiveData<Int>
    ): LiveData<PagedList<Movie>> {

        return Transformations.switchMap(movieIdLiveData) {
            initializeSimilarMoviesPagedListBuilder(it, scope).build()
        }
    }

    /**Returns similar shows with paging to the [ShowDetailsFragment]*/
    fun getSimilarShowsList(
        showIdLiveData: MutableLiveData<Int>
    ): LiveData<PagedList<TvShow>> {

        return Transformations.switchMap(showIdLiveData) {
            initializeSimilarShowsPagedListBuilder(it, scope).build()
        }
    }

    private fun initializeSimilarShowsPagedListBuilder(
        showId: Int,
        scope: CoroutineScope
    ): LivePagedListBuilder<Int, TvShow> {
        val factory = object : DataSource.Factory<Int, TvShow>() {
            override fun create(): DataSource<Int, TvShow> {
                return SimilarShowDataSource(scope, showId)
            }
        }
        return LivePagedListBuilder(factory, config)
    }

    private fun initializeSearchMoviePagedListBuilder(
        query: String,
        scope: CoroutineScope
    ): LivePagedListBuilder<Int, Movie> {

        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return SearchedMovieDataSource(scope, query)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    private fun initializeSimilarMoviesPagedListBuilder(
        movieId: Int,
        scope: CoroutineScope
    ): LivePagedListBuilder<Int, Movie> {

        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return SimilarMovieDataSource(scope, movieId)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }
}