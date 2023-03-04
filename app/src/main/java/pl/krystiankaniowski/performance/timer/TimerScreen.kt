package pl.krystiankaniowski.performance.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme

@Composable
fun TimerScreen(viewModel: TimerViewModel) {
    TimerScreenContent(
        state = viewModel.state.collectAsState().value,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun TimerScreenContent(
    state: TimerViewModel.State,
    onEvent: (TimerViewModel.Event) -> Unit,
) {
    Column {
        Text(state.counter)
        Text("is active = " + state.isTimerActive)
        Button(
            enabled = state.isStartButtonEnabled,
            onClick = {
                onEvent(TimerViewModel.Event.Start)
            },
            content = { Text("Start") },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TimerScreenContentPreview() {
    PerformanceTheme {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                TimerScreenContent(
                    state = TimerViewModel.State(
                        counter = "25:00",
                        isTimerActive = false,
                        isStartButtonEnabled = true,
                    ),
                    onEvent = {},
                )
            }
        }
    }
}