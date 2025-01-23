package com.app.fishbowlInterview.ui.jokeDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.fishbowlInterview.R
import com.app.fishbowlInterview.data.models.Joke
import com.app.fishbowlInterview.ui.jokeList.CategoryChip
import kotlinx.serialization.Serializable

@Serializable
data class JokeDetailScreen(val jokeId: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeDetailMain(
    navController: NavController,
    viewModel: JokeDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.errorLoading) {
        if (uiState.errorLoading) {
            // An improvement here could be to show an error message to the user
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.joke),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 16.dp, end = 17.dp, top = 16.dp)
            ) {
                uiState.joke?.let { jokeInfo ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.format_quote_open),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .width(37.dp)
                        )
                        Icon(
                            painter = painterResource(R.drawable.format_quote_close),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .width(37.dp)
                        )
                    }
                    Text(
                        text = when (jokeInfo.joke) {
                            is Joke.SingleJoke -> jokeInfo.joke.joke
                            is Joke.TwoPartJoke -> jokeInfo.joke.setup + "\n" + jokeInfo.joke.delivery
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 11.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 1.dp, top = 2.dp)
                    ) {
                        CategoryChip(
                            category = jokeInfo.joke.category
                        )
                        Icon(
                            painter = painterResource(
                                if (jokeInfo.isFavorite) {
                                    R.drawable.heart
                                } else {
                                    R.drawable.heart_outline
                                }
                            ),
                            contentDescription = stringResource(
                                if (jokeInfo.isFavorite) {
                                    R.string.remove_from_favorites
                                } else {
                                    R.string.add_to_favorites
                                }
                            ),
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .width(40.dp)
                                .clickable {
                                    viewModel.handleFavoriteTapped(jokeInfo.isFavorite)
                                }
                        )
                    }
                } ?: run {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp)
                    )
                }
            }
        }
    )
}
