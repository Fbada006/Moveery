package com.disruption.moveery.network

import com.disruption.moveery.models.Result
import com.disruption.moveery.models.SearchedResult
import com.disruption.moveery.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**OkHttp for basic retrofit setup*/
//TODO: Get rid of this logging
val interceptor: HttpLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

/**The client to set up retrofit*/
val client = OkHttpClient.Builder()
    .callTimeout(30, TimeUnit.SECONDS)
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .addInterceptor(interceptor)
    .retryOnConnectionFailure(true)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.BASE_URL)
    .client(client)
    .build()

/**
 * A public interface that exposes the [getDiscoverMoviesAsync] method
 */
interface MovieApiService {

    /**Returns movies on the landing page*/
    @GET("discover/{type}")
    fun getDiscoverMoviesAsync(
        @Path("type") type: String = "movie",
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String? = "popularity.desc",
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Deferred<Result>

    /**Searches the movie*/
    @GET("search/movie")
    suspend fun getMoviesByKeyword(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") boolean: Boolean = false,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<SearchedResult>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MovieApi {
    val movieRetrofitService: MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }
}