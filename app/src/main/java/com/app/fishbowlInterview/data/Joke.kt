package com.app.fishbowlInterview.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
sealed class Joke(type: JokeType) {
    abstract val category: JokeCategory
    abstract val flags: JokeFlags
    abstract val id: Int

    @JsonClass(generateAdapter = true)
    data class TwoPartJoke(
        override val category: JokeCategory,
        override val flags: JokeFlags,
        override val id: Int,
        val setup: String,
        val delivery: String,
    ) : Joke(type = JokeType.TWO_PART)

    @JsonClass(generateAdapter = true)
    data class SingleJoke(
        override val category: JokeCategory,
        override val flags: JokeFlags,
        override val id: Int,
        val joke: String,
    ) : Joke(type = JokeType.SINGLE)
}
