package com.app.fishbowlInterview.ui.jokeList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.fishbowlInterview.data.Joke
import com.app.fishbowlInterview.ui.jokeDetail.JokeDetailScreen
import kotlinx.serialization.Serializable

@Serializable
object JokeListScreen

@Composable
fun JokeListMain(
    navController: NavController,
    viewModel: JokeListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    //TODO
    val firstJoke = uiState.jokes.firstOrNull()
    Text(
        text = when (firstJoke) {
            is Joke.SingleJoke -> firstJoke.joke
            is Joke.TwoPartJoke -> firstJoke.setup + "\n" + firstJoke.delivery
            null -> "Loading..."
        },
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxSize().clickable {
            navController.navigate(JokeDetailScreen)
        }
    )
}
