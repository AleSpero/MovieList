package com.sperolabs.movielist.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sperolabs.movielist.network.MovieListEndpoint
import com.sperolabs.movielist.network.ShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesGson() : Gson =
        GsonBuilder()
            .create()

    @Singleton
    @Provides
    fun provideRetrofit(gson : Gson) : Retrofit =
        Retrofit.Builder()
            .baseUrl("https://www.episodate.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun provideMovieEndpoint(retrofit: Retrofit) : MovieListEndpoint =
        retrofit.create(MovieListEndpoint::class.java)

    @Singleton
    @Provides
    fun provideRepository(endpoint: MovieListEndpoint) : ShowRepository =
        ShowRepository(endpoint)


}