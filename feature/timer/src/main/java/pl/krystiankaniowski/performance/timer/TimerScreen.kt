package pl.krystiankaniowski.performance.timer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.krystiankaniowski.performance.model.Seconds
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    viewModel: TimerViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToStats: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.timer_title)) },
                actions = {
                    IconButton(
                        content = { Icon(Icons.Default.ShowChart, null) },
                        onClick = onNavigateToStats,
                    )
                    IconButton(
                        content = { Icon(Icons.Default.History, null) },
                        onClick = onNavigateToHistory,
                    )
                    IconButton(
                        content = { Icon(Icons.Default.Settings, null) },
                        onClick = onNavigateToSettings,
                    )
                },
            )
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            TimerScreenContent(
                state = viewModel.state.collectAsState().value,
                onEvent = viewModel::onEvent,
            )
        }
    }
}

@Composable
fun TimerScreenContent(
    state: TimerViewModel.State,
    onEvent: (TimerViewModel.Event) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.size(196.dp),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(196.dp)
                    .border(
                        width = 6.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = CircleShape,
                    ),
            )
            if (state.isTimerActive) {
                CircularProgressIndicator(
                    modifier = Modifier.size(196.dp),
                    progress = 1 - state.progress,
                    strokeCap = StrokeCap.Round,
                )
            }
            Text(text = state.counter, fontSize = 48.sp)
        }
        Spacer(modifier = Modifier.height(48.dp))
        when (state.button) {
            is TimerViewModel.State.Button.Cancel -> OutlinedButton(
                content = { Text(stringResource(R.string.timer_button_cancel, state.button.secondsLeft)) },
                onClick = { onEvent(TimerViewModel.Event.Cancel) },
            )
            TimerViewModel.State.Button.Start -> OutlinedButton(
                content = { Text(stringResource(R.string.timer_button_start)) },
                onClick = { onEvent(TimerViewModel.Event.Start) },
            )
            TimerViewModel.State.Button.Stop -> OutlinedButton(
                content = { Text(stringResource(R.string.timer_button_stop)) },
                onClick = { onEvent(TimerViewModel.Event.Stop) },
            )
        }
    }
}

@Preview
@Composable
fun TimerScreenContentPreview_Start() {
    PerformanceTheme {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                TimerScreenContent(
                    state = TimerViewModel.State(
                        counter = "25:00",
                        isTimerActive = false,
                        button = TimerViewModel.State.Button.Start,
                        progress = 0f,
                    ),
                    onEvent = {},
                )
            }
        }
    }
}

@Preview
@Composable
fun TimerScreenContentPreview_Stop() {
    PerformanceTheme {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                TimerScreenContent(
                    state = TimerViewModel.State(
                        counter = "25:00",
                        isTimerActive = true,
                        button = TimerViewModel.State.Button.Stop,
                        progress = .25f,
                    ),
                    onEvent = {},
                )
            }
        }
    }
}

@Preview
@Composable
fun TimerScreenContentPreview_Cancel() {
    PerformanceTheme {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                TimerScreenContent(
                    state = TimerViewModel.State(
                        counter = "25:00",
                        isTimerActive = true,
                        button = TimerViewModel.State.Button.Cancel(Seconds(10)),
                        progress = .75f,
                    ),
                    onEvent = {},
                )
            }
        }
    }
}