package me.uditverma.sample.composeperformance.ui.step0

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import me.uditverma.sample.composeperformance.LogFilter
import me.uditverma.sample.composeperformance.Logger
import me.uditverma.sample.composeperformance.ui.Item
import java.util.Random
import kotlin.math.absoluteValue

@Composable
fun ComposePerformanceScreen() {
    Logger.d(
        message = "Recomposing entire Screen",
        filter = LogFilter.Recomposition
    )
    Logger.d(
        message = "Recreating item list",
        filter = LogFilter.ReAllocation
    )
    val random = Random(10)
    val items = IntRange(0, 100).map {
        val randomNumber = random.nextInt().absoluteValue % 200
        Item(
            desc = "[$randomNumber] Item with index = $it",
            id = randomNumber
        )
    }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    Logger.d(
        message = "Recalculating scroll progress",
        filter = LogFilter.ReAllocation
    )
    val scrollProgress = scrollState.value / (scrollState.maxValue * 1f)
    Logger.d(
        message = "Recalculating showScrollToTopButton",
        filter = LogFilter.ReAllocation
    )
    val showScrollToTopButton = scrollProgress > .5
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ItemList(
            items = items,
            scrollState = scrollState,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ScrollPositionIndicator(progress = scrollProgress)
        Box(
            modifier = Modifier
                .height(64.dp)
                .align(Alignment.CenterHorizontally)
        ) {
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
private fun ItemList(
    modifier: Modifier = Modifier,
    items: List<Item>,
    scrollState: ScrollState = rememberScrollState()
) {
    Logger.d(
        message = "Recomposing ItemList",
        filter = LogFilter.Recomposition
    )
    Column(
        modifier = modifier
            .verticalScroll(scrollState),
    ) {
        Logger.d(
            message = "Sorting item list",
            filter = LogFilter.ReAllocation
        )
        for (item in items.sorted()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.desc)
            }
        }
    }
}

@Composable
private fun ScrollPositionIndicator(
    modifier: Modifier = Modifier,
    progress: Float
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Color.Yellow)
    ) {
        Logger.d(
            message = "Recomposing ScrollPositionIndicator",
            filter = LogFilter.Recomposition
        )
        Logger.d(
            message = "Recalculating progressWidth",
            filter = LogFilter.ReAllocation
        )
        val progressWidth = maxWidth - (8.dp)
        Logger.d(
            message = "Recalculating item offset",
            filter = LogFilter.ReAllocation
        )
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

@Composable
private fun ScrollToTopButton(
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