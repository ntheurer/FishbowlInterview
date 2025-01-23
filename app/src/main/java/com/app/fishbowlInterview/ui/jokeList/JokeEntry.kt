package com.app.fishbowlInterview.ui.jokeList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.fishbowlInterview.data.models.Joke
import com.app.fishbowlInterview.data.models.JokeCategory
import com.app.fishbowlInterview.data.models.JokeFlags
import com.app.fishbowlInterview.ui.theme.FishbowlInterviewTheme
import com.app.fishbowlInterview.ui.theme.Grey100
import com.app.fishbowlInterview.ui.theme.TextSecondary

@Composable
fun JokeEntry(
    joke: Joke,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(start = 16.dp, top = 8.dp)
    ) {
        Text(
            text = when (joke) {
                is Joke.SingleJoke -> joke.joke
                is Joke.TwoPartJoke -> joke.setup + "\n" + joke.delivery
            },
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier
        )
        CategoryChip(
            category = joke.category,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 1.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 8.dp)
                .background(Grey100)
        )
    }
}

@Preview
@Composable
private fun SingleJokeEntryPreview() {
    FishbowlInterviewTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {
            JokeEntry(
                joke = Joke.SingleJoke(
                    category = JokeCategory.PUN,
                    flags = JokeFlags(false, false, false, false, false, false),
                    id = 1,
                    joke = "7 days without a pun makes one weak"
                ),
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun TwoPartJokeEntryPreview() {
    FishbowlInterviewTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {
            JokeEntry(
                joke = Joke.TwoPartJoke(
                    category = JokeCategory.PUN,
                    flags = JokeFlags(false, false, false, false, false, false),
                    id = 1,
                    setup = "Did you know the first French fries weren't actually cooked in France",
                    delivery = "They were cooked in Greece"
                ),
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
