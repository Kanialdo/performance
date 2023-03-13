package pl.krystiankaniowski.performance.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
            modifier = Modifier.size(128.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (state.isTimerActive) {
                CircularProgressIndicator(modifier = Modifier.size(128.dp))
            }
            Text(text = state.counter, style = MaterialTheme.typography.headlineLarge)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                enabled = state.isStartButtonEnabled,
                onClick = { onEvent(TimerViewModel.Event.Start) },
                content = { Text("Start") },
            )
            Button(
                enabled = state.isStopButtonEnabled,
                onClick = { onEvent(TimerViewModel.Event.Stop) },
                content = { Text("Stop") },
            )
        }
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
                        isStopButtonEnabled = false,
                    ),
                    onEvent = {},
                )
            }
        }
    }
}