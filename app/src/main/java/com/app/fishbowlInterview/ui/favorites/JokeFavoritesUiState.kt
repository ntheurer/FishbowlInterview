package com.app.fishbowlInterview.ui.favorites

import com.app.fishbowlInterview.data.models.Joke

data class JokeFavoritesUiState(
    val jokes: List<Joke> = listOf(),
    val isLoading: Boolean = true
)
