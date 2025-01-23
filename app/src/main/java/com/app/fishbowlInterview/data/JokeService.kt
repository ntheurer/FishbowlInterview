package com.app.fishbowlInterview.data

import com.app.fishbowlInterview.data.models.JokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokeService {
    @GET("joke/{category}?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&amount=25")
    suspend fun getJokes(
        @Path("category") category: String,
        @Query("contains") searchTerm: String?
    ): Response<JokeResponse>
}
