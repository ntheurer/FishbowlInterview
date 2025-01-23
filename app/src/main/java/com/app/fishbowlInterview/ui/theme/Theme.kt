package com.app.fishbowlInterview.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    background = Color.Black,
    onBackground = TextPrimaryDark,
    outline = Grey12,
    outlineVariant = GreyDark,
    surface = GreyDark,
    onSurface = TextTertiaryDark
)

private val LightColorScheme = lightColorScheme(
    background = Color.White,
    onBackground = TextPrimaryLight,
    outline = Grey12,
    outlineVariant = GreyDark,
    surface = Grey100,
    onSurface = TextTertiaryLight
)

@Composable
fun FishbowlInterviewTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}