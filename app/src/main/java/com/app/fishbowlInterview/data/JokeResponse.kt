package com.app.fishbowlInterview.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JokeResponse(
    val error: Boolean,
    val amount: Int,
    val jokes: List<Joke>
)
