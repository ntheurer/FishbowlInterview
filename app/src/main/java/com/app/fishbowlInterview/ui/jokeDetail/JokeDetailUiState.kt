package com.app.fishbowlInterview.ui.jokeDetail

import com.app.fishbowlInterview.data.models.JokeEntity

data class JokeDetailUiState(
    val joke: JokeEntity? = null,
    val errorLoading: Boolean = false
)
