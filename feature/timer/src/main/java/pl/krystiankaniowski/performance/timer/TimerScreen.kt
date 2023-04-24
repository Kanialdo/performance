package pl.krystiankaniowski.performance.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.krystiankaniowski.performance.model.Seconds
import pl.krystiankaniowski.performance.ui.components.UiTag
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    viewModel: TimerViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit,
    onNavigateToStats: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.timer_title)) },
                actions = {
                    IconButton(
                        content = { Icon(Icons.Default.List, null) },
                        onClick = onNavigateToStats,
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
private fun TimerScreenContent(
    state: TimerViewModel.State,
    onEvent: (TimerViewModel.Event) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.size(128.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (state.isTimerActive) {
                CircularProgressIndicator(modifier = Modifier.size(128.dp))
            }
            Text(text = state.counter, style = MaterialTheme.typography.headlineLarge)
        }
        Spacer(modifier = Modifier.height(32.dp))
        when (state.button) {
            is TimerViewModel.State.Button.Cancel -> Button(
                content = { Text(stringResource(R.string.timer_button_cancel, state.button.secondsLeft)) },
                onClick = { onEvent(TimerViewModel.Event.Cancel) },
            )

            TimerViewModel.State.Button.Start -> Button(
                content = { Text(stringResource(R.string.timer_button_start)) },
                onClick = { onEvent(TimerViewModel.Event.Start) },
            )

            TimerViewModel.State.Button.Stop -> Button(
                content = { Text(stringResource(R.string.timer_button_stop)) },
                onClick = { onEvent(TimerViewModel.Event.Stop) },
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Tag(
            name = "Undefined",
            onClick = { onEvent(TimerViewModel.Event.OnTagClick) },
        )
    }
}

@Composable
private fun Tag(
    name: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        content = { Text(name) },
        onClick = onClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TimerScreenContentPreview_Start() {
    PerformanceTheme {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                TimerScreenContent(
                    state = TimerViewModel.State(
                        counter = "25:00",
                        isTimerActive = false,
                        button = TimerViewModel.State.Button.Start,
                        tag = UiTag("tag", Color.Yellow),
                    ),
                    onEvent = {},
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TimerScreenContentPreview_Stop() {
    PerformanceTheme {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                TimerScreenContent(
                    state = TimerViewModel.State(
                        counter = "25:00",
                        isTimerActive = true,
                        button = TimerViewModel.State.Button.Stop,
                        tag = UiTag("tag", Color.Yellow),
                    ),
                    onEvent = {},
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TimerScreenContentPreview_Cancel() {
    PerformanceTheme {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                TimerScreenContent(
                    state = TimerViewModel.State(
                        counter = "25:00",
                        isTimerActive = true,
                        button = TimerViewModel.State.Button.Cancel(Seconds(10)),
                        tag = UiTag("tag", Color.Yellow),
                    ),
                    onEvent = {},
                )
            }
        }
    }
}