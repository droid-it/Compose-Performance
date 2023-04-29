//package me.uditverma.sample.composeperformance.ui
//
//import androidx.compose.foundation.ScrollState
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxWithConstraints
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.Immutable
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.launch
//import java.util.Random
//
//@Composable
//fun ComposePerformanceScreenFix() {
//    val items = remember {
//        val random = Random(10)
//        IntRange(0, 100).map {
//            val randomNumber = random.nextInt()
//            "($randomNumber) Item number $it"
//        }.sorted()
//    }
//    val scrollState = rememberScrollState()
//    val scope = rememberCoroutineScope()
//    val showScrollToTopButton by remember {
//        derivedStateOf {
//            scrollState.value / (scrollState.maxValue * 1f) > .5
//        }
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        ItemListFix(
//            items = Data(items),
//            scrollState = scrollState,
//            modifier = Modifier.weight(1f)
//        )
//        ScrollPositionIndicatorFix(progress = { scrollState.value / (scrollState.maxValue * 1f) })
//        Box(
//            modifier = Modifier
//                .height(64.dp)
//                .align(Alignment.CenterHorizontally)
//        ) {
//            if (showScrollToTopButton) {
//                ScrollToTopButtonFix {
//                    scope.launch {
//                        scrollState.scrollTo(0)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ItemListFix(
//    modifier: Modifier = Modifier,
//    items: Data,
//    scrollState: ScrollState = rememberScrollState()
//) {
//    Column(
//        modifier = modifier
//            .verticalScroll(scrollState)
//    ) {
//        for (item in items.items) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(32.dp)
//            ) {
//                Text(text = item)
//            }
//        }
//    }
//}
//
//@Composable
//fun ScrollPositionIndicatorFix(
//    modifier: Modifier = Modifier,
//    progress: () -> Float
//) {
//    BoxWithConstraints(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(32.dp)
//            .background(Color.Yellow)
//    ) {
//        val progressWidth = remember(maxWidth) { maxWidth - (8.dp) }
//        Box(
//            modifier = Modifier
//                .height(1.dp)
//                .width(progressWidth)
//                .background(Color.Gray)
//                .align(Alignment.Center)
//        )
//        Box(
//            modifier = Modifier
//                .offset {
//                    IntOffset((((progressWidth - 16.dp) * progress()) + 4.dp).toPx().toInt(), 0)
//                }
//                .size(16.dp)
//                .align(Alignment.CenterStart)
//                .background(Color.Red)
//        )
//    }
//}
//
//@Composable
//fun ScrollToTopButtonFix(
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit
//) {
//    Button(
//        onClick = onClick,
//        modifier = modifier
//    ) {
//        Text(text = "Scroll To Top")
//    }
//}
//
//@Immutable
//data class Data(
//    val items: List<String>
//)