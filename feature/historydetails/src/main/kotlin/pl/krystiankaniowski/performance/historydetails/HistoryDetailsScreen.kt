package pl.krystiankaniowski.performance.historydetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import pl.krystiankaniowski.performance.ui.components.PerformanceLoadingScreen
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme

@Composable
fun HistoryDetailsScreen(
    viewModel: HistoryDetailsViewModel,
    navigateUp: () -> Unit,
) {

    HistoryDetailsContent(
        state = viewModel.state.collectAsState().value,
        navigateUp = navigateUp,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryDetailsContent(
    state: HistoryDetailsViewModel.State,
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.history_details_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = navigateUp,
                        content = { Icon(Icons.Default.ArrowBack, null) },
                    )
                },
            )
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            when (state) {
                HistoryDetailsViewModel.State.Loading -> PerformanceLoadingScreen()
                is HistoryDetailsViewModel.State.Loaded -> HistoryDetailsContentLoaded(
                    state = state,
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.HistoryDetailsContentLoaded(state: HistoryDetailsViewModel.State.Loaded) {
    ListItem(
        headlineText = { Text("Start") },
        supportingText = { Text(state.startDate) },
    )
    ListItem(
        headlineText = { Text("End") },
        supportingText = { Text(state.endDate) },
    )
}

@Preview
@Composable
private fun HistoryDetailsContent_Loading_Preview() {
    PerformanceTheme {
        HistoryDetailsContent(
            state = HistoryDetailsViewModel.State.Loading,
            navigateUp = {},
        )
    }
}

@Preview
@Composable
private fun HistoryDetailsContent_Loaded_Preview() {
    PerformanceTheme {
        HistoryDetailsContent(
            state = HistoryDetailsViewModel.State.Loaded(
                startDate = "2020-02-02 10:15:20",
                endDate = "2020-02-02 10:30:20",
            ),
            navigateUp = {},
        )
    }
}