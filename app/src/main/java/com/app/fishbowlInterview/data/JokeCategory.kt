package com.app.fishbowlInterview.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.ToJson

enum class JokeCategory {
    ANY,
    MISC,
    PROGRAMMING,
    DARK,
    PUN,
    SPOOKY,
    CHRISTMAS;

    companion object {
        val jokeCategoryAdapter: JsonAdapter<JokeCategory> = object : JsonAdapter<JokeCategory>() {
            @FromJson
            override fun fromJson(reader: com.squareup.moshi.JsonReader): JokeCategory? {
                return JokeCategory.valueOf(reader.nextString().uppercase())
            }

            @ToJson
            override fun toJson(writer: com.squareup.moshi.JsonWriter, value: JokeCategory?) {
                writer.value(value?.name?.lowercase()) // Convert enum to string (endpoint is not case sensitive)
            }
        }
    }
}
