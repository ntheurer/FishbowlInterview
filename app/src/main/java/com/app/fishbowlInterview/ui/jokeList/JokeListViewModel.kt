package com.app.fishbowlInterview.ui.jokeList

import androidx.lifecycle.ViewModel
import com.app.fishbowlInterview.data.Joke
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class JokeListViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(JokeListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        //FIXME call backend to grab jokes. Maybe use mediator data?
        _uiState.update {
            it.copy(
                jokes = listOf(Joke("7 days without a pun makes one weak")),
                isLoading = false
            )
        }
    }
}
