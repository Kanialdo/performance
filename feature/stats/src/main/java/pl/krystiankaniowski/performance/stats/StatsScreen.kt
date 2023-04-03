package pl.krystiankaniowski.performance.stats

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import pl.krystiankaniowski.performance.ui.components.PerformanceEmptyScreen
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
                StatsViewModel.State.Empty -> PerformanceEmptyScreen(stringResource(R.string.stats_empty_title))
                is StatsViewModel.State.Loaded -> StatsScreenContent(
                    state = state,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StatsScreenContent(
    state: StatsViewModel.State.Loaded,
) {
    LazyColumn {
        state.items.forEach {
            stickyHeader {
                StatsScreenHeader(it.key)
            }
            items(it.value) {
                StatsScreenItem(it)
            }
        }
    }
}

@Composable
private fun StatsScreenHeader(
    item: StatsViewModel.State.Loaded.Item.Header,
) {
    ListItem(headlineText = { Text(item.date) })
}

@Composable
private fun StatsScreenItem(
    item: StatsViewModel.State.Loaded.Item.Focus,
) {
    ListItem(
        headlineText = { Text("Focus") },
        trailingContent = { Text(item.duration) },
    )
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
                        items = mapOf(
                            StatsViewModel.State.Loaded.Item.Header("10-10-2020") to listOf(
                                StatsViewModel.State.Loaded.Item.Focus(
                                    "10 min",
                                ),
                                StatsViewModel.State.Loaded.Item.Focus(
                                    "12 min",
                                ),
                            ),
                        ),
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun StatsScreenHeaderPreview() {
    PerformanceTheme {
        StatsScreenHeader(StatsViewModel.State.Loaded.Item.Header("10-10-2020"))
    }
}

@Preview
@Composable
private fun StatsScreenItemPreview() {
    PerformanceTheme {
        StatsScreenItem(StatsViewModel.State.Loaded.Item.Focus("12 min"))
    }
}