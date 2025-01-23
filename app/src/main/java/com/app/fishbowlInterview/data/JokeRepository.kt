package com.app.fishbowlInterview.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokeRepository @Inject constructor(
    private val jokeService: JokeService
) {
    suspend fun getJokes(
        category: JokeCategory,
        searchTerm: String?
    ): List<Joke> {
        //TODO: be able to change the endpoint call for paging
        val response = jokeService.getJokes(
            category = category.name,
            searchTerm = searchTerm
        )
        return when {
            response.isSuccessful && response.body() != null -> {
                response.body()?.jokes.orEmpty()
            }

            else -> {
                //TODO improve error handling
                listOf()
            }
        }
    }
}
