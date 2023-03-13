package pl.krystiankaniowski.performance.stats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.krystiankaniowski.performance.ui.components.PerformanceLoadingScreen
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme

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
                actions = {
                    IconButton(
                        content = { Icon(Icons.Default.Refresh, null) },
                        onClick = { viewModel.onEvent(StatsViewModel.Event.Refresh) },
                    )
                },
            )
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            when (val state = viewModel.state.collectAsState().value) {
                StatsViewModel.State.Loading -> PerformanceLoadingScreen()
                is StatsViewModel.State.Loaded -> StatsScreenContent(
                    state = state,
                )
            }
        }
    }
}

@Composable
fun StatsScreenContent(
    state: StatsViewModel.State.Loaded,
) {
    LazyColumn {
        items(state.items) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(it.startDate)
                Text(it.endDate)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun StatsScreenContentPreview() {
    PerformanceTheme {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                StatsScreenContent(
                    state = StatsViewModel.State.Loaded(
                        items = listOf(
                            StatsViewModel.State.Loaded.FocusEntry(
                                "10-10-2020 10:10:00",
                                "10-10-2020 10:20:00",
                            ),
                            StatsViewModel.State.Loaded.FocusEntry(
                                "10-10-2020 10:30:00",
                                "10-10-2020 10:40:00",
                            ),
                        ),
                    ),
                )
            }
        }
    }
}