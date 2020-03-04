package com.disruption.moveery.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.disruption.moveery.R
import com.disruption.moveery.data.MovieRoomDatabase
import com.disruption.moveery.network.MovieApiService
import com.disruption.moveery.repo.MovieRepo
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [AppModuleBinds::class])
object AppModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideGlideInstance(
        context: Context,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(context)
            .setDefaultRequestOptions(requestOptions)
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.movie_loading_animation)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.ic_broken_image)
    }

    @Singleton
    @JvmStatic
    @Provides
    fun providesMovieApi(retrofit: Retrofit): MovieApiService = retrofit.create(MovieApiService::class.java)

    @Singleton
    @JvmStatic
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

    @Singleton
    @JvmStatic
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()


    @Singleton
    @JvmStatic
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)


    @Singleton
    @JvmStatic
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

    @Singleton
    @JvmStatic
    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String = "https://api.themoviedb.org/4/"

    @JvmStatic
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
}

@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: MovieRepo): MovieRepo
}