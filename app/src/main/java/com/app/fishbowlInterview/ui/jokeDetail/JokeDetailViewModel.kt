package com.app.fishbowlInterview.ui.jokeDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.app.fishbowlInterview.data.JokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val jokeRepository: JokeRepository
) : ViewModel() {

    private val args = savedStateHandle.toRoute<JokeDetailScreen>()

    private val _uiState = MutableStateFlow(JokeDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            jokeRepository.getJoke(args.jokeId)?.let { joke ->
                _uiState.update {
                    it.copy(
                        joke = joke,
                    )
                }
            } ?: run {
                // Unable to find the joke
                // This could be improved by then calling the server to get the joke, but since this
                // is unlikely to be entered, this is a future improvement that could be made later
                _uiState.update {
                    it.copy(
                        errorLoading = true
                    )
                }
            }
        }
    }

    fun handleFavoriteTapped(wasFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.value.joke?.let { oldJoke ->
                val updatedJoke = oldJoke.copy(
                    isFavorite = !wasFavorite,
                    joke = oldJoke.joke
                )
                jokeRepository.updateJokeInDb(updatedJoke)
                _uiState.update {
                    it.copy(
                        joke = updatedJoke
                    )
                }
            }
        }
    }
}
