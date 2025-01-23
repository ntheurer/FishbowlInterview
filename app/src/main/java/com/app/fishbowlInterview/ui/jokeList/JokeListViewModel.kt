package com.app.fishbowlInterview.ui.jokeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.fishbowlInterview.data.JokeCategory
import com.app.fishbowlInterview.data.JokeRepository
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

    private suspend fun fetchJokes() {
        val jokes = jokeRepository.getJokes(
            category = uiState.value.currentFilter,
            searchTerm = uiState.value.currentSearchTerm
        )
        _uiState.update {
            it.copy(
                jokes = jokes,
                isLoading = false
            )
        }
    }
}
