package me.uditverma.sample.composeperformance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.uditverma.sample.composeperformance.ui.step0.ComposePerformanceScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePerformanceScreen()
//            Screen()
        }
    }
}