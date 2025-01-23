package com.app.fishbowlInterview.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// Sealed Classes can't be saved in the db as an entity so instead wrap it
@Entity(tableName = "joke_table")
data class JokeEntity(
    @PrimaryKey val id: Int,
    val joke: Joke,
    var isFavorite: Boolean = false
)
