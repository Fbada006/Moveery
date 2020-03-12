package com.disruption.moveery.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.disruption.moveery.MovieApplication
import com.disruption.moveery.R
import com.disruption.moveery.data.MovieBoundaryCallBack
import com.disruption.moveery.data.MovieLocalCache
import com.disruption.moveery.data.MovieRoomDatabase
import com.disruption.moveery.di.viewmodelfactory.ViewModelsModule
import com.disruption.moveery.network.MovieApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
        moshi: Moshi,
        okHttpClient: OkHttpClient,
        @Named("baseUrl") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    /**Provide moshi*/
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()

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
}

