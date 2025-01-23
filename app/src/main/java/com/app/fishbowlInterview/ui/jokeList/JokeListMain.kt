package com.app.fishbowlInterview.ui.jokeList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.app.fishbowlInterview.ui.jokeDetail.JokeDetailScreen
import com.app.fishbowlInterview.ui.theme.Grey100
import com.app.fishbowlInterview.ui.theme.Grey12
import com.app.fishbowlInterview.ui.theme.TextPrimary
import com.app.fishbowlInterview.ui.theme.TextTertiary
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
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Grey100)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.jokes),
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            BoxWithConstraints {
                val dropdownWidth = maxWidth * 0.75f
                Icon(
                    painter = painterResource(uiState.currentFilter.icon),
                    contentDescription = stringResource(R.string.filter_content_description),
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
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            onClick = {
                                viewModel.filter(option)
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }
            Icon(
                painter = painterResource(R.drawable.heart),
                contentDescription = stringResource(R.string.favorite_nav_content_description),
                modifier = Modifier
                    .padding(start = 24.dp)
                    .width(24.dp)
                    .clickable {
                        //todo: go to favorites screen
                    }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Grey100)
                .padding(top = 11.dp, start = 10.dp, end = 12.dp, bottom = 15.dp)
        ) {
            OutlinedTextField(
                value = searchTerm,
                onValueChange = { searchTerm = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_placeholder),
                        color = TextTertiary,
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = TextPrimary,
                    focusedIndicatorColor = Grey12,
                    unfocusedIndicatorColor = Grey12,
                    focusedPlaceholderColor = TextTertiary,
                    unfocusedPlaceholderColor = TextTertiary
                ),
                textStyle = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(R.string.search),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall,
                color = TextPrimary,
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
        ) {
            items(
                items = uiState.jokes,
                key = { it.id }
            ) { joke ->
                JokeEntry(
                    joke = joke,
                    onClick = {
                        navController.navigate(JokeDetailScreen) //todo: use joke.id
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}
