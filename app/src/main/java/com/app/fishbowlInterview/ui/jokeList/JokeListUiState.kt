package com.app.fishbowlInterview.ui.jokeList

import com.app.fishbowlInterview.data.models.Joke
import com.app.fishbowlInterview.data.models.JokeCategory

data class JokeListUiState(
    val jokes: List<Joke> = listOf(),
    val isLoading: Boolean = true,
    val currentFilter: JokeCategory = JokeCategory.ANY,
    val currentSearchTerm: String? = null
)
