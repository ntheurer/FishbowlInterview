package com.app.fishbowlInterview.ui.jokeList

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.fishbowlInterview.data.models.JokeCategory
import com.app.fishbowlInterview.data.JokeRepository
import com.app.fishbowlInterview.data.models.JokeError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeListViewModel @Inject constructor(
    private val jokeRepository: JokeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JokeListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchJokes()
        }
    }

    fun search(searchTerm: String) {
        if (searchTerm.isBlank()) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    jokes = emptyList(),
                    isLoading = true,
                    currentSearchTerm = searchTerm
                )
            }
            fetchJokes()
        }
    }

    fun filter(category: JokeCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    jokes = emptyList(),
                    isLoading = true,
                    currentFilter = category
                )
            }
            fetchJokes()
        }
    }

    @VisibleForTesting
    suspend fun fetchJokes() {
        jokeRepository.fetchJokes(
            category = uiState.value.currentFilter,
            searchTerm = uiState.value.currentSearchTerm,
            handleError = ::handleError
        )?.let { jokes ->
            _uiState.update { state ->
                val oldJokeMap = state.jokes.associateBy { it.id }
                val newJokeMap = jokes.associateBy { it.id }
                if (oldJokeMap.keys.containsAll(newJokeMap.keys)) {
                    // Pause future calls to the backend since we have fetched all the jokes available
                    state.copy(
                        isLoading = false,
                        pausePagination = true,
                        errorMessage = null
                    )
                } else {
                    state.copy(
                        jokes = (state.jokes + jokes).associateBy { it.id }.values.toList(),
                        isLoading = false,
                        pausePagination = false,
                        errorMessage = null
                    )
                }
            }
        }
    }

    @VisibleForTesting
    fun handleError(error: JokeError) {
        val errorMessage = if (uiState.value.jokes.isEmpty()) {
            // Only show an error message if there are no jokes to be shown
            "Error: " + (error.message ?: "A server error was encountered. Please try again later")
        } else {
            null
        }
        _uiState.update { state ->
            state.copy(
                pausePagination = true,
                errorMessage = errorMessage,
                isLoading = false
            )
        }
    }

    fun fetchMoreItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                )
            }
            // Note: with more time this could be improved by using some sort of cursor
            // so we can continue fetching 25 more jokes each time instead of possible repeats
            fetchJokes()
        }
    }
}
