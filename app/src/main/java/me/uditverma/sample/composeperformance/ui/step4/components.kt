package me.uditverma.sample.composeperformance.ui.step4

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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
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
    val items = remember {
        Logger.d(
            message = "Recreating item list",
            filter = LogFilter.ReAllocation
        )
        val random = Random(10)
        IntRange(0, 100).map {
            val randomNumber = random.nextInt().absoluteValue % 200
            Item(
                desc = "[$randomNumber] Item with index = $it",
                id = randomNumber
            )
        }.sorted()
    }

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ItemList(
            itemHolder = ItemHolder(
                items
            ),
            scrollState = scrollState,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ScrollPositionIndicator(progress = { scrollState.value / (scrollState.maxValue * 1f) })
        Box(
            modifier = Modifier
                .height(64.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            ScrollToTopButton(isVisible = {
                Logger.d(
                    message = "Recalculating showScrollToTopButton",
                    filter = LogFilter.ReAllocation
                )
                scrollState.value / (scrollState.maxValue * 1f) > .5
            }) {
                scope.launch {
                    scrollState.scrollTo(0)
                }
            }
        }
    }
}

@Composable
private fun ItemList(
    modifier: Modifier = Modifier,
    itemHolder: ItemHolder,
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
        for (item in itemHolder.items) {
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
    progress: () -> Float
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
        val progressWidth = remember(maxWidth) {
            Logger.d(
                message = "Recalculating progressWidth",
                filter = LogFilter.ReAllocation
            )
            maxWidth - (8.dp)
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
                .offset {
                    IntOffset(
                        (((progressWidth - 16.dp) * progress()) + 4.dp)
                            .toPx()
                            .toInt(), 0
                    )
                }
                .size(16.dp)
                .align(Alignment.CenterStart)
                .background(Color.Red)
        )
    }
}

@Composable
private fun ScrollToTopButton(
    isVisible: () -> Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    if (!isVisible()) return
    Logger.d(
        message = "Recomposing ScrollToTopButton",
        filter = LogFilter.Recomposition
    )
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = "Scroll To Top")
    }
}

@Immutable
private data class ItemHolder(
    val items: List<Item>
)