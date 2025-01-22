package com.app.fishbowlInterview.ui.jokeList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.fishbowlInterview.ui.jokeDetail.JokeDetailScreen
import kotlinx.serialization.Serializable

@Serializable
object JokeListScreen

@Composable
fun JokeListMain(
    navController: NavController,
    viewModel: JokeListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    //TODO
    Text(
        text = uiState.jokes.firstOrNull()?.content ?: "Loading...",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxSize().clickable {
            navController.navigate(JokeDetailScreen)
        }
    )
}
