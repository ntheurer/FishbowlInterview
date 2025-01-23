package com.app.fishbowlInterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.app.fishbowlInterview.ui.Navigation
import com.app.fishbowlInterview.ui.theme.FishbowlInterviewTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FishbowlInterviewTheme {
                Navigation()
            }
        }
    }
}
