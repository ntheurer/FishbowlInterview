package com.app.fishbowlInterview.data

import retrofit2.Response
import retrofit2.http.GET

interface JokeService {
    @GET("joke/Any?blacklistFlags=nsfw&amount=25")
    suspend fun getJokes(): Response<JokeResponse>
}
