package com.app.fishbowlInterview.ui.jokeList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.fishbowlInterview.R
import com.app.fishbowlInterview.data.models.Joke

@Composable
fun PaginatedLazyColumn(
    jokes: List<Joke>,
    onJokeClicked: (Int) -> Unit,
    loadMoreItems: () -> Unit,
    listState: LazyListState,
    isLoading: () -> Boolean,
    pausePagination: () -> Boolean,
    getErrorMessage: () -> String?,
    modifier: Modifier = Modifier,
    buffer: Int = 4
) {
    val shouldLoadMore by remember {
        derivedStateOf {
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            totalItemsCount > 0 &&
                    lastVisibleItemIndex >= (totalItemsCount - buffer) &&
                    !isLoading() &&
                    !pausePagination()
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            loadMoreItems()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            items = jokes,
            key = { it.id }
        ) { joke ->
            JokeEntry(
                joke = joke,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onJokeClicked(joke.id)
                    }
            )
        }
        if (isLoading()) {
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
        if (!isLoading() && jokes.isEmpty() || getErrorMessage() != null) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = getErrorMessage() ?: stringResource(R.string.no_jokes_found),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}
