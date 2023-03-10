package me.uditverma.sample.composeperformance.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.util.Random

@Composable
fun ComposePerformanceScreen() {
    val random = Random(10)
    val items = IntRange(0, 100).map {
        val randomNumber = random.nextInt()
        "($randomNumber) Item number $it"
    }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val scrollProgress = scrollState.value / (scrollState.maxValue * 1f)
    val showScrollToTopButton = scrollProgress > .5
    println("composing *ComposePerformanceScreen*")
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ItemList(
            items = items,
            scrollState = scrollState,
            modifier = Modifier.weight(1f)
        )
        ScrollPositionIndicator(progress = scrollProgress)
        Box(
            modifier = Modifier.height(64.dp)
                .align(Alignment.CenterHorizontally)
        ) {
//            ScrollProgressBar(progress = scrollProgress)
            if (showScrollToTopButton) {
                ScrollToTopButton {
                    scope.launch {
                        scrollState.scrollTo(0)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    items: List<String>,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        for (item in items.sorted()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            ) {
                Text(text = item)
            }
        }
    }
}

@Composable
fun ScrollPositionIndicator(
    modifier: Modifier = Modifier,
    progress: Float
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Color.Yellow)
    ) {
        val progressWidth = maxWidth - (8.dp)
        val xOffset = with(LocalDensity.current) {
            ((progressWidth - 16.dp).toPx() * progress).toDp() + 4.dp
        }
        Box(
            modifier = Modifier
                .height(1.dp)
                .width(progressWidth)
                .background(Color.Gray)
                .align(Alignment.Center)
        )
        Box(
            modifier = Modifier
                .offset(xOffset, 0.dp)
                .size(16.dp)
                .align(Alignment.CenterStart)
                .background(Color.Red)
        )
    }
}

//@Composable
//fun ScrollProgressBar(
//    modifier: Modifier = Modifier,
//    progress: Float
//) {
//    LinearProgressIndicator(
//        progress = progress,
//        modifier = modifier
//    )
//}

@Composable
fun ScrollToTopButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = "Scroll To Top")
    }
}