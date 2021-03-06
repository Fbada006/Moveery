package com.droidafricana.moveery.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.droidafricana.moveery.MovieApplication
import com.droidafricana.moveery.R
import com.droidafricana.moveery.data.MovieLocalCache
import com.droidafricana.moveery.data.MovieRoomDatabase
import com.droidafricana.moveery.network.MovieApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**The main module to inject dependencies*/
@Module(includes = [ViewModelsModule::class])
class AppModule {

    /**Provide the local movie cache*/
    @Singleton
    @Provides
    fun provideLocalCache(movieRoomDatabase: MovieRoomDatabase): MovieLocalCache =
        MovieLocalCache(movieRoomDatabase)

    /**Provide the Glide instance*/
    @Singleton
    @Provides
    fun provideGlideInstance(
        context: Context,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(context)
            .setDefaultRequestOptions(requestOptions)
    }

    /**Provide the request options for Glide*/
    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.movie_loading_animation)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.ic_broken_image)
    }

    /**Provide the api for network calls*/
    @Singleton
    @Provides
    fun providesMovieApi(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

    /**Provide the retrofit instance*/
    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        @Named("baseUrl") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /**Provide the interceptor*/
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    /**Provide the client*/
    @Provides
    fun providesOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**Provide the base url of retrofit*/
    @Singleton
    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String = "https://api.themoviedb.org/4/"

    /**Provide the [MovieRoomDatabase]*/
    @Singleton
    @Provides
    fun provideDataBase(context: Context): MovieRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieRoomDatabase::class.java,
            "movies_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    /**Provide the [Context]*/
    @Singleton
    @Provides
    fun provideContext(application: MovieApplication): Context {
        return application.applicationContext
    }

    /**Provide the [CoroutineScope]*/
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    /**Provides the shared preferences*/
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
}

