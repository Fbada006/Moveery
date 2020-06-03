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
import com.disruption.moveery.mappers.toMovieDomainModel
import com.disruption.moveery.mappers.toShowDomainModel
import com.disruption.moveery.models.movies.Movie
import com.disruption.moveery.models.shows.TvShow
import com.disruption.moveery.models.videos.Video
import com.disruption.moveery.models.videos.VideoResult
import com.disruption.moveery.network.MovieApi
import com.disruption.moveery.utils.Constants.DATABASE_PAGE_SIZE
import com.disruption.moveery.utils.Constants.MOVIE_TYPE
import com.disruption.moveery.utils.Constants.SHOW_TYPE
import com.disruption.moveery.utils.Resource
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import javax.inject.Inject

/**
 * Repository class that works with local and remote data sources.
 * This class supplies data to the ViewModel to be displayed
 */
@Suppress("KDocUnresolvedReference")
class MovieRepo @Inject constructor(
    private val movieLocalCache: MovieLocalCache,
    private val movieBoundaryCallBack: MovieBoundaryCallBack,
    private val showBoundaryCallBack: ShowBoundaryCallBack,
    private val scope: CoroutineScope
) : IMovieRepo {

    private val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .build()

    private val videosLiveData = MutableLiveData<Resource<List<Video>>>()
    private lateinit var videosDeferred: VideoResult

    //This is bad for testing but once the API starts serving the data in V4, it will be injected
    private val movieApiService = MovieApi.movieRetrofitService

    /**Get all the movies to from the local storage*/
    override fun getAllMovies(): LiveData<PagedList<Movie>> {
        val dataSourceFactory = movieLocalCache.getMovieData()

        return LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(movieBoundaryCallBack)
            .build()
    }

    /**Get all the movies from the local storage*/
    override fun getAllShows(): LiveData<PagedList<TvShow>> {
        val factory = movieLocalCache.getShowsData()

        return LivePagedListBuilder(factory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(showBoundaryCallBack)
            .build()
    }

    /**Get all fav movies*/
    override fun getAllFavMovies(): LiveData<PagedList<Movie>> {
        val factory =
            movieLocalCache.getFavMovieData().map { it.toMovieDomainModel() }
        return LivePagedListBuilder(factory, DATABASE_PAGE_SIZE)
            .build()
    }

    /**Get all fav shows*/
    override fun getAllFavShows(): LiveData<PagedList<TvShow>> {
        val factory =
            movieLocalCache.getFavShowsData().map { it.toShowDomainModel() }
        return LivePagedListBuilder(factory, DATABASE_PAGE_SIZE).build()
    }

    /**Returns the searched movie with paging involved*/
    override fun getSearchedMovieList(
        queryLiveData: MutableLiveData<String>
    ): LiveData<PagedList<Movie>> {

        return Transformations.switchMap(queryLiveData) {
            initializeSearchMoviePagedListBuilder(it, scope).build()
        }
    }

    /**Returns the searched show with paging involved*/
    override fun getSearchedShowList(
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
    override fun getSimilarMovieList(
        movieIdLiveData: MutableLiveData<Int>
    ): LiveData<PagedList<Movie>> {

        return Transformations.switchMap(movieIdLiveData) {
            initializeSimilarMoviesPagedListBuilder(it, scope).build()
        }
    }

    /**Returns similar shows with paging to the [ShowDetailsFragment]*/
    override fun getSimilarShowsList(
        showIdLiveData: MutableLiveData<Int>
    ): LiveData<PagedList<TvShow>> {

        return Transformations.switchMap(showIdLiveData) {
            initializeSimilarShowsPagedListBuilder(it, scope).build()
        }
    }

    /**
     * Gets all the videos of either a movie or the show
     * @param type is the string denoting a [Movie] or [TvShow]
     *
     */
    override suspend fun getVideos(type: String, id: Int): MutableLiveData<Resource<List<Video>>> {
        try {
            if (type == MOVIE_TYPE) {
                videosLiveData.value = Resource.loading(null)
                videosDeferred = movieApiService.getMovieVideosAsync(id).await()
                Timber.e("Videos from the movie ----------------- $${videosDeferred.results}")
                videosLiveData.value = Resource.success(videosDeferred.results)
            } else if (type == SHOW_TYPE) {
                videosLiveData.value = Resource.loading(null)
                videosDeferred = movieApiService.getTvShowVideosAsync(id).await()
                videosLiveData.value = Resource.success(videosDeferred.results)
                Timber.e("Videos from the show ----------------- $${videosDeferred.results}")
            }
        } catch (ex: Exception) {
            Timber.e("Error loading videos data --------- $ex")
            videosLiveData.value = ex.message?.let { Resource.error(it, null) }
        }
        return videosLiveData
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