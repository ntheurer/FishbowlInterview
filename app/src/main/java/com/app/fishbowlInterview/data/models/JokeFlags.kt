package com.app.fishbowlInterview.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JokeFlags(
    val nsfw: Boolean,
    val religious: Boolean,
    val political: Boolean,
    val racist: Boolean,
    val sexist: Boolean,
    val explicit: Boolean
)
