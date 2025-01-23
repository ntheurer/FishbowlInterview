package com.app.fishbowlInterview.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.fishbowlInterview.data.JokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeFavoritesViewModel @Inject constructor(
    private val jokeRepository: JokeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(JokeFavoritesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            jokeRepository.watchFavoriteJokes().distinctUntilChanged().collect { favorites ->
                _uiState.update { state ->
                    state.copy(
                        jokes = favorites.map { it.joke },
                        isLoading = false
                    )
                }
            }
        }
    }
}
