package com.app.fishbowlInterview.ui.jokeList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.app.fishbowlInterview.ui.jokeDetail.JokeDetailScreen
import kotlinx.serialization.Serializable

@Serializable
object JokeListScreen

@Composable
fun JokeListMain(navController: NavController) {
    //TODO
    Text(
        text = "Hello World!",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxSize().clickable {
            navController.navigate(JokeDetailScreen)
        }
    )
}