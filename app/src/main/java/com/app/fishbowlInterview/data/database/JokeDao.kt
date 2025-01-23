package com.app.fishbowlInterview.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.app.fishbowlInterview.data.models.JokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    @Query("SELECT * FROM joke_table WHERE id = :id LIMIT 1")
    suspend fun getJoke(id: Int): JokeEntity?

    @Query("SELECT * FROM joke_table WHERE isFavorite = 1")
    abstract fun getFavoriteJokes(): Flow<List<JokeEntity>>

    @Upsert
    suspend fun upsertJokes(jokes: List<JokeEntity>)
}
