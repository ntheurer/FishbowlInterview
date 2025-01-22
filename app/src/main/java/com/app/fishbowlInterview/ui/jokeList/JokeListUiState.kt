package com.app.fishbowlInterview.ui.jokeList

import com.app.fishbowlInterview.data.Joke

data class JokeListUiState(
    val jokes: List<Joke> = listOf(),
    val isLoading: Boolean = true
)
