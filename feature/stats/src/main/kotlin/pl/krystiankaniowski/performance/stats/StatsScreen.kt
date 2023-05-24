package pl.krystiankaniowski.performance.stats

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.krystiankaniowski.performance.ui.components.PerformanceLoadingScreen
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.stats_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = navigateUp,
                        content = { Icon(Icons.Default.ArrowBack, null) },
                    )
                },
            )
        },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            when (val state = viewModel.state.collectAsState().value) {
                StatsViewModel.State.Loading -> PerformanceLoadingScreen()
                is StatsViewModel.State.Daily -> StatsScreenContentDaily(
                    state = state,
                )
            }
        }
    }
}

@Composable
fun StatsScreenContentDaily(state: StatsViewModel.State.Daily) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                enabled = false,
                onClick = { /*TODO*/ },
                content = { Icon(imageVector = Icons.Default.ArrowLeft, contentDescription = null) },
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = state.date,
                textAlign = TextAlign.Center,
            )
            IconButton(
                enabled = false,
                onClick = { /*TODO*/ },
                content = { Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null) },
            )
        }
        Divider()
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
//            androidx.compose.material3.ListItem(
//                headlineContent = { Text("Focus time") },
//                supportingContent = { Text(text = state.total, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary) },
//            )
//            androidx.compose.material3.ListItem(
//                headlineContent = { Text("Focus distribution") },
//                supportingContent = {
//                    DailyStat(
//                        data = state.chartData,
//                    )
//                },
//            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Focus time")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = state.total, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Focus distribution")
                    Spacer(modifier = Modifier.height(4.dp))
                    DailyStat(
                        data = state.chartData,
                    )
                }
            }

//            Text("Focus time")
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = state.total, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
//            Spacer(modifier = Modifier.height(32.dp))
//            Text("Focus distribution")
//            Spacer(modifier = Modifier.height(16.dp))
//            DailyStat(
//                data = state.chartData,
//            )
        }
    }
}

@Suppress("EmptyFunctionBlock", "UnusedPrivateMember")
@Composable
private fun DailyStat(modifier: Modifier = Modifier, data: List<StatsViewModel.FocusTime>) {
    val c = Color.Gray.copy(alpha = 0.5f)
    Column(modifier = modifier.padding(top = 8.dp, bottom = 8.dp)) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .border(1.dp, c),
        ) {
            val color = MaterialTheme.colorScheme.primary
            data.forEach {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(
                        color = color,
                        topLeft = Offset(x = this.size.width * it.started, y = 0f),
                        size = Size(width = this.size.width * it.width, height = this.size.height),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "", fontSize = 14.sp, color = c)
            Text(text = " 4:00", fontSize = 14.sp, color = c)
            Text(text = " 8:00", fontSize = 14.sp, color = c)
            Text(text = "12:00", fontSize = 14.sp, color = c)
            Text(text = "16:00", fontSize = 14.sp, color = c)
            Text(text = "20:00", fontSize = 14.sp, color = c)
            Text(text = "", fontSize = 14.sp, color = c)
        }
    }
}

@Preview
@Composable
private fun StatsScreenContentDaily_Preview() {
    PerformanceTheme {
        Scaffold {
            Box(modifier = Modifier.padding(it)) {
                StatsScreenContentDaily(
                    state = StatsViewModel.State.Daily(
                        date = "2020-02-02",
                        total = "1h 30 min",
                        chartData = listOf(
                            StatsViewModel.FocusTime(10.hours.inWholeMilliseconds, 10.hours.inWholeMilliseconds + 25.minutes.inWholeMilliseconds),
                            StatsViewModel.FocusTime(11.hours.inWholeMilliseconds, 11.hours.inWholeMilliseconds + 25.minutes.inWholeMilliseconds),
                        ),
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun DailyStat_Preview() {
    PerformanceTheme {
        DailyStat(
            data = listOf(
                StatsViewModel.FocusTime(10.hours.inWholeMilliseconds, 10.hours.inWholeMilliseconds + 25.minutes.inWholeMilliseconds),
                StatsViewModel.FocusTime(11.hours.inWholeMilliseconds, 11.hours.inWholeMilliseconds + 25.minutes.inWholeMilliseconds),
            ),
        )
    }
}