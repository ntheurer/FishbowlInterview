package com.app.fishbowlInterview.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.app.fishbowlInterview.data.models.JokeEntity

@Dao
interface JokeDao {
    @Query("SELECT * FROM joke_table WHERE id = :id LIMIT 1")
    suspend fun getJoke(id: Int): JokeEntity?

    @Upsert
    suspend fun upsertJokes(jokes: List<JokeEntity>)
}
