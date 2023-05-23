package pl.krystiankaniowski.performance.stats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.hilt.navigation.compose.hiltViewModel
import pl.krystiankaniowski.performance.ui.components.PerformanceEmptyScreen
import pl.krystiankaniowski.performance.ui.components.PerformanceLoadingScreen
import pl.krystiankaniowski.performance.ui.components.PerformanceUnderDevelopmentScreen
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
            )
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            Box(modifier = Modifier.padding(it)) {
                when (val state = viewModel.state.collectAsState().value) {
                    StatsViewModel.State.Loading -> PerformanceLoadingScreen()
                    StatsViewModel.State.UnderDevelopment -> PerformanceUnderDevelopmentScreen()
                    StatsViewModel.State.Empty -> PerformanceEmptyScreen(stringResource(R.string.stats_empty_title))
                    is StatsViewModel.State.Loaded -> StatsScreenContent(
                        state = state,
                    )
                }
            }
        }
    }
}

@Suppress("EmptyFunctionBlock", "UnusedPrivateMember")
@Composable
private fun StatsScreenContent(state: StatsViewModel.State.Loaded) {
}

@Preview
@Composable
private fun StatsScreenContent_Preview() {
    PerformanceTheme {
        StatsScreenContent(
            state = StatsViewModel.State.Loaded,
        )
    }
}