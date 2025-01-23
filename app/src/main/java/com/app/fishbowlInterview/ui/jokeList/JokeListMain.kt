package com.app.fishbowlInterview.ui.jokeList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.fishbowlInterview.R
import com.app.fishbowlInterview.data.models.JokeCategory
import com.app.fishbowlInterview.ui.favorites.JokeFavoritesScreen
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

    var dropdownExpanded by remember {
        mutableStateOf(false)
    }

    var searchTerm by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.jokes),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            BoxWithConstraints {
                val dropdownWidth = maxWidth * 0.75f
                Icon(
                    painter = painterResource(uiState.currentFilter.icon),
                    contentDescription = stringResource(R.string.filter_content_description),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .width(24.dp)
                        .clickable {
                            dropdownExpanded = !dropdownExpanded
                        }
                )
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                    modifier = Modifier
                        .width(dropdownWidth)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    JokeCategory.entries.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                FilterOption(
                                    option = option,
                                    isHighlighted = option == uiState.currentFilter,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            onClick = {
                                viewModel.filter(option)
                                dropdownExpanded = false
                            },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .background(
                                    if (option == uiState.currentFilter) {
                                        MaterialTheme.colorScheme.surface
                                    } else {
                                        MaterialTheme.colorScheme.background
                                    }
                                )
                        )
                    }
                }
            }
            Icon(
                painter = painterResource(R.drawable.heart),
                contentDescription = stringResource(R.string.favorite_nav_content_description),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(start = 24.dp)
                    .width(24.dp)
                    .clickable {
                        navController.navigate(JokeFavoritesScreen)
                    }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(top = 11.dp, start = 10.dp, end = 12.dp, bottom = 15.dp)
        ) {
            OutlinedTextField(
                value = searchTerm,
                onValueChange = { searchTerm = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_placeholder),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                    focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                ),
                textStyle = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(R.string.search),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .clickable {
                        viewModel.search(searchTerm)
                    }
                    .padding(horizontal = 8.dp, vertical = 10.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(
                items = uiState.jokes,
                key = { it.id }
            ) { joke ->
                JokeEntry(
                    joke = joke,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(JokeDetailScreen(jokeId = joke.id))
                        }
                )
            }
            if (uiState.isLoading) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, start = 16.dp)
                                .background(MaterialTheme.colorScheme.surface)
                        )
                    }
                }
            }
            if (!uiState.isLoading && uiState.jokes.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.no_jokes_found),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}
