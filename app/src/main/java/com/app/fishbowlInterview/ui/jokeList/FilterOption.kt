package com.app.fishbowlInterview.ui.jokeList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.fishbowlInterview.data.models.JokeCategory
import com.app.fishbowlInterview.ui.theme.GreyDark
import com.app.fishbowlInterview.ui.theme.TextPrimary

@Composable
fun FilterOption(
    option: JokeCategory,
    isHighlighted: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = stringResource(option.displayName),
            textAlign = TextAlign.Center,
            color = TextPrimary,
            style = MaterialTheme.typography.labelLarge
        )
        Icon(
            painter = painterResource(option.icon),
            contentDescription = null,
            tint = if (isHighlighted) {
                TextPrimary
            } else {
                GreyDark
            },
            modifier = Modifier
                .width(24.dp)
        )
    }
}