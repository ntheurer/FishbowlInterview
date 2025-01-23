package com.app.fishbowlInterview.data

import com.app.fishbowlInterview.data.database.AppDatabase
import com.app.fishbowlInterview.data.models.Joke
import com.app.fishbowlInterview.data.models.JokeCategory
import com.app.fishbowlInterview.data.models.JokeEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokeRepository @Inject constructor(
    private val jokeService: JokeService,
    private val db: AppDatabase
) {
    suspend fun fetchJokes(
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
                val jokes = response.body()?.jokes.orEmpty()
                val jokeEntities = jokes.map { joke ->
                    JokeEntity(
                        id = joke.id,
                        joke = joke
                    )
                }
                db.jokeDao().upsertJokes(jokeEntities)
                jokes
            }

            else -> {
                //TODO improve error handling
                listOf()
            }
        }
    }

    suspend fun getJoke(id: Int): JokeEntity? {
        return db.jokeDao().getJoke(id)
    }

    suspend fun updateJokeInDb(joke: JokeEntity) {
        db.jokeDao().upsertJokes(
            listOf(joke)
        )
    }
}
