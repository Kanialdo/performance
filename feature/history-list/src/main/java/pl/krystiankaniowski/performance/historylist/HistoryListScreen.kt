package pl.krystiankaniowski.performance.historylist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun HistoryListScreen(
    viewModel: HistoryListViewModel = hiltViewModel(),
    openAddItemScreen: () -> Unit,
    openDetailsScreen: (Long) -> Unit,
    navigateUp: () -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.effects.collect {
            when (it) {
                is HistoryListViewModel.Effect.OpenDetails -> openDetailsScreen(it.id)
                HistoryListViewModel.Effect.OpenAddItem -> openAddItemScreen()
            }
        }
    }

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
                        content = { Icon(Icons.Default.Add, null) },
                        onClick = { viewModel.onEvent(HistoryListViewModel.Event.OnAddItemClick) },
                    )
                },
            )
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            when (val state = viewModel.state.collectAsState().value) {
                HistoryListViewModel.State.Loading -> PerformanceLoadingScreen()
                HistoryListViewModel.State.Empty -> PerformanceEmptyScreen(stringResource(R.string.stats_empty_title))
                is HistoryListViewModel.State.Loaded -> HistoryListContent(
                    state = state,
                    onClick = viewModel::onItemClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HistoryListContent(
    state: HistoryListViewModel.State.Loaded,
    onClick: (Long) -> Unit,
) {
    LazyColumn {
        state.items.forEach {
            stickyHeader {
                HistoryListHeader(it.key)
            }
            items(it.value) {
                HistoryListItem(item = it, onClick = onClick)
            }
        }
    }
}

@Composable
private fun HistoryListHeader(
    item: HistoryListViewModel.State.Loaded.Item.Header,
) {
    ListItem(headlineContent = { Text(item.date) })
}

@Composable
private fun HistoryListItem(
    item: HistoryListViewModel.State.Loaded.Item.Focus,
    onClick: (Long) -> Unit,
) {
    ListItem(
        modifier = Modifier.clickable { onClick(item.id) },
        headlineContent = { Text("Focus") },
        trailingContent = { Text(item.duration) },
    )
}

@Preview
@Composable
fun HistoryListContent_Preview() {
    PerformanceTheme {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                HistoryListContent(
                    state = HistoryListViewModel.State.Loaded(
                        items = mapOf(
                            HistoryListViewModel.State.Loaded.Item.Header("10-10-2020") to listOf(
                                HistoryListViewModel.State.Loaded.Item.Focus(
                                    id = 1,
                                    duration = "10 min",
                                ),
                                HistoryListViewModel.State.Loaded.Item.Focus(
                                    id = 2,
                                    duration = "12 min",
                                ),
                            ),
                        ),
                    ),
                    onClick = {},
                )
            }
        }
    }
}

@Preview
@Composable
private fun HistoryListHeader_Preview() {
    PerformanceTheme {
        HistoryListHeader(HistoryListViewModel.State.Loaded.Item.Header("10-10-2020"))
    }
}

@Preview
@Composable
private fun HistoryListItem_Preview() {
    PerformanceTheme {
        HistoryListItem(
            item = HistoryListViewModel.State.Loaded.Item.Focus(id = 1, duration = "12 min"),
            onClick = {},
        )
    }
}