package com.app.fishbowlInterview.ui.jokeList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.fishbowlInterview.data.models.JokeCategory
import com.app.fishbowlInterview.ui.theme.FishbowlInterviewTheme

@Composable
fun CategoryChip(
    category: JokeCategory,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = category.tint.copy(alpha = .15f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Icon(
            painter = painterResource(category.icon),
            contentDescription = null,
            tint = category.tint,
            modifier = Modifier
                .width(15.dp)
        )
        Text(
            text = stringResource(category.displayName),
            style = MaterialTheme.typography.labelMedium,
            color = category.tint,
            modifier = Modifier
                .padding(start = 4.dp)
        )
    }
}

@Preview
@Composable
private fun CategoryChipsPreview() {
    FishbowlInterviewTheme {
        LazyColumn {
            items(
                items = JokeCategory.entries
            ) {
                CategoryChip(it)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
