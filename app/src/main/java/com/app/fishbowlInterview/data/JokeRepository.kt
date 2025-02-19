package com.app.fishbowlInterview.data

import com.app.fishbowlInterview.data.database.AppDatabase
import com.app.fishbowlInterview.data.models.Joke
import com.app.fishbowlInterview.data.models.JokeCategory
import com.app.fishbowlInterview.data.models.JokeEntity
import com.app.fishbowlInterview.data.models.JokeError
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokeRepository @Inject constructor(
    private val jokeService: JokeService,
    private val db: AppDatabase,
    moshi: Moshi
) {
    private val errorAdapter: JsonAdapter<JokeError> = moshi.adapter(JokeError::class.java)

    suspend fun fetchJokes(
        category: JokeCategory,
        searchTerm: String?,
        handleError: (JokeError) -> Unit
    ): List<Joke>? {
        // This could be improved to use a cursor for paging for infinite scrolling
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
                try {
                    response.errorBody()?.string()?.let { errorBody ->
                        errorAdapter.fromJson(errorBody)?.let {
                            handleError(it)
                        }
                    }
                } catch (exception: Exception) {
                    // Nothing needed here, this is just to avoid a potential crash
                }
                null
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

    fun watchFavoriteJokes(): Flow<List<JokeEntity>> = db.jokeDao().getFavoriteJokes()
}
