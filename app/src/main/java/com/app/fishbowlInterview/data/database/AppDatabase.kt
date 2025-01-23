package com.app.fishbowlInterview.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.fishbowlInterview.data.models.JokeEntity

@Database(
    entities = [JokeEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}
