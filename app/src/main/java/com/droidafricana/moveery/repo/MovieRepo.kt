package com.droidafricana.moveery.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.droidafricana.moveery.data.MovieLocalCache
import com.droidafricana.moveery.data.movies.MovieBoundaryCallBack
import com.droidafricana.moveery.data.movies.search.SearchedMovieDataSource
import com.droidafricana.moveery.data.movies.similar.SimilarMovieDataSource
import com.droidafricana.moveery.data.shows.ShowBoundaryCallBack
import com.droidafricana.moveery.data.shows.search.SearchedShowDataSource
import com.droidafricana.moveery.data.shows.similar.SimilarShowDataSource
import com.droidafricana.moveery.mappers.toFavMovieDataModel
import com.droidafricana.moveery.mappers.toFavShowDataModel
import com.droidafricana.moveery.mappers.toMovieDomainModel
import com.droidafricana.moveery.mappers.toShowDomainModel
import com.droidafricana.moveery.models.movies.LandingMovieResource
import com.droidafricana.moveery.models.movies.Movie
import com.droidafricana.moveery.models.shows.LandingShowResource
import com.droidafricana.moveery.models.shows.TvShow
import com.droidafricana.moveery.models.videos.Video
import com.droidafricana.moveery.network.MovieApi
import com.droidafricana.moveery.utils.Constants.DATABASE_PAGE_SIZE
import com.droidafricana.moveery.utils.Constants.MOVIE_TYPE
import com.droidafricana.moveery.utils.Constants.SHOW_TYPE
import com.droidafricana.moveery.utils.Resource
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

    //This is bad for testing but once the API starts serving the data in V4, it will be injected
    private val movieApiService = MovieApi.movieRetrofitService

    /**Get all the movies to from the local storage*/
    override fun getLandingMovies(): LandingMovieResource {
        val dataSourceFactory = movieLocalCache.getMovieData()

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(movieBoundaryCallBack)
            .build()

        val errors = movieBoundaryCallBack.networkErrors
        val loadingStatus = movieBoundaryCallBack.loadingStatus

        return LandingMovieResource(data, loadingStatus, errors)
    }

    /**Get all the movies from the local storage*/
    override fun getLandingShows(): LandingShowResource {
        val factory = movieLocalCache.getShowsData()

        val data = LivePagedListBuilder(factory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(showBoundaryCallBack)
            .build()

        val errors = showBoundaryCallBack.networkErrors
        val loadingStatus = showBoundaryCallBack.loadingStatus

        return LandingShowResource(data, loadingStatus, errors)
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
        videosLiveData.value = Resource.loading(null)
        try {
            if (type == MOVIE_TYPE) {
                val videosDeferred = movieApiService.getMovieVideosAsync(id).await()
                Timber.e("Videos from the movie ----------------- $${videosDeferred.results}")
                videosLiveData.value = Resource.success(videosDeferred.results)
            } else if (type == SHOW_TYPE) {
                val videosDeferred = movieApiService.getTvShowVideosAsync(id).await()
                videosLiveData.value = Resource.success(videosDeferred.results)
                Timber.e("Videos from the show ----------------- $${videosDeferred.results}")
            }
        } catch (ex: Exception) {
            Timber.e("Error loading videos data --------- $ex")
            videosLiveData.value = ex.message?.let { Resource.error(it, null) }
        }
        return videosLiveData
    }

    /**Save movie to fav*/
    override suspend fun insertMovieToFav(movie: Movie) {
        movieLocalCache.insertMovieToFav(movie.toFavMovieDataModel())
    }

    /**Save show to fav*/
    override suspend fun insertShowToFav(show: TvShow) {
        movieLocalCache.insertShowToFav(show.toFavShowDataModel())
    }

    /**Delete a movie from fav*/
    override suspend fun deleteMovieFromFav(id: Int) {
        movieLocalCache.deleteMovieFromFav(id)
    }

    /**Delete a show from fav*/
    override suspend fun deleteShowFromFav(id: Int) {
        movieLocalCache.deleteShowFromFav(id)
    }

    override suspend fun nukeMovieFavourites() {
        movieLocalCache.nukeMovieFavourites()
    }

    override suspend fun nukeShowFavourites() {
        movieLocalCache.nukeShowFavourites()
    }

    /**Get a movie based on its id*/
    override fun getMovieById(id: Int?): LiveData<Movie?> =
        Transformations.map(movieLocalCache.getMovieById(id)) {
            it?.toMovieDomainModel()
        }

    /**Get a show based on its id*/
    override fun getShowById(id: Int?): LiveData<TvShow?> =
        Transformations.map(movieLocalCache.getShowById(id)) {
            it?.toShowDomainModel()
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