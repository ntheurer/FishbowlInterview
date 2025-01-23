package com.app.fishbowlInterview.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JokeError(
    val internalError: Boolean? = null,
    val code: Int? = null,
    val message: String? = null,
    val causedBy: List<String>? = null,
    val additionalInfo: String? = null
)
