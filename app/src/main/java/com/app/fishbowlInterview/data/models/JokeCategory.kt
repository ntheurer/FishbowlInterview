package com.app.fishbowlInterview.data.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.app.fishbowlInterview.R
import com.app.fishbowlInterview.ui.theme.Blue500
import com.app.fishbowlInterview.ui.theme.Green500
import com.app.fishbowlInterview.ui.theme.Indigo500
import com.app.fishbowlInterview.ui.theme.Orange500
import com.app.fishbowlInterview.ui.theme.Pink500
import com.app.fishbowlInterview.ui.theme.Purple500
import com.app.fishbowlInterview.ui.theme.Red500
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.ToJson

enum class JokeCategory(
    @StringRes val displayName: Int,
    @DrawableRes val icon: Int,
    val tint: Color
) {
    ANY(R.string.any, R.drawable.view_grid_outline, Indigo500),
    MISC(R.string.misc, R.drawable.cards_diamond_outline, Blue500),
    PROGRAMMING(R.string.programming, R.drawable.apple_keyboard_command, Green500),
    CHRISTMAS(R.string.christmas, R.drawable.gift_outline, Red500),
    PUN(R.string.pun, R.drawable.emoticon_happy_outline, Pink500),
    DARK(R.string.dark, R.drawable.weather_night, Purple500),
    SPOOKY(R.string.spooky, R.drawable.ghost_outline, Orange500);

    companion object {
        val jokeCategoryAdapter: JsonAdapter<JokeCategory> = object : JsonAdapter<JokeCategory>() {
            @FromJson
            override fun fromJson(reader: com.squareup.moshi.JsonReader): JokeCategory? {
                return JokeCategory.valueOf(reader.nextString().uppercase())
            }

            @ToJson
            override fun toJson(writer: com.squareup.moshi.JsonWriter, value: JokeCategory?) {
                writer.value(value?.name?.lowercase()) // Convert enum to string (endpoint is not case sensitive)
            }
        }
    }
}
