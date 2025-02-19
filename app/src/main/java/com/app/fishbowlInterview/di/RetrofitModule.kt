package com.app.fishbowlInterview.di

import android.content.Context
import androidx.room.Room
import com.app.fishbowlInterview.data.JokeService
import com.app.fishbowlInterview.data.database.AppDatabase
import com.app.fishbowlInterview.data.models.Joke
import com.app.fishbowlInterview.data.models.JokeCategory
import com.app.fishbowlInterview.data.models.JokeType
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideBaseUrl(): String = "https://v2.jokeapi.dev/"

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(Joke::class.java, "type")
                    .withSubtype(Joke.SingleJoke::class.java, JokeType.SINGLE.endpointValue)
                    .withSubtype(Joke.TwoPartJoke::class.java, JokeType.TWO_PART.endpointValue)
            )
            .add(JokeCategory::class.java, JokeCategory.jokeCategoryAdapter)
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideJokeService(
        retrofit: Retrofit
    ): JokeService = retrofit.create(JokeService::class.java)

    @Provides
    @Singleton
    fun provideDB(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database"
        ).build()
    }
}
