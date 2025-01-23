package com.app.fishbowlInterview.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.fishbowlInterview.ui.jokeDetail.JokeDetailMain
import com.app.fishbowlInterview.ui.jokeDetail.JokeDetailScreen
import com.app.fishbowlInterview.ui.jokeList.JokeListMain
import com.app.fishbowlInterview.ui.jokeList.JokeListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = JokeListScreen) {
        composable<JokeListScreen> {
            JokeListMain(navController)
        }
        composable<JokeDetailScreen> {
            JokeDetailMain(navController)
        }
    }
}
