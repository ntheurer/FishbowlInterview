package com.app.fishbowlInterview.data.database

import androidx.room.TypeConverter
import com.app.fishbowlInterview.data.models.Joke
import com.app.fishbowlInterview.data.models.JokeCategory
import com.app.fishbowlInterview.data.models.JokeType
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Converters {
    private val moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(Joke::class.java, "type")
                .withSubtype(Joke.SingleJoke::class.java, JokeType.SINGLE.endpointValue)
                .withSubtype(Joke.TwoPartJoke::class.java, JokeType.TWO_PART.endpointValue)
        )
        .add(JokeCategory::class.java, JokeCategory.jokeCategoryAdapter)
        .add(KotlinJsonAdapterFactory())
        .build()
    private val jokeAdapter: JsonAdapter<Joke> = moshi.adapter(Joke::class.java)

    @TypeConverter
    fun fromJoke(joke: Joke?): String? {
        return joke?.let { jokeAdapter.toJson(it) }
    }

    @TypeConverter
    fun toJoke(json: String?): Joke? {
        return json?.let { jokeAdapter.fromJson(it) }
    }

    @TypeConverter
    fun fromJokeCategory(value: JokeCategory): String {
        return value.name
    }

    @TypeConverter
    fun toJokeCategory(value: String): JokeCategory {
        return JokeCategory.valueOf(value)
    }
}
