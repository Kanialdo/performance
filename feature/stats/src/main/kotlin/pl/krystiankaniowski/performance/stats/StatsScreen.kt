package pl.krystiankaniowski.performance.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.krystiankaniowski.performance.model.Seconds
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
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            when (val state = viewModel.state.collectAsState().value) {
                StatsViewModel.State.Loading -> PerformanceLoadingScreen()
                StatsViewModel.State.UnderDevelopment -> PerformanceUnderDevelopmentScreen()
                is StatsViewModel.State.Loaded -> StatsScreenContent(
                    state = state,
                )

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
                onClick = { /*TODO*/ },
                content = { Icon(imageVector = Icons.Default.ArrowLeft, contentDescription = null) },
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.date,
                textAlign = TextAlign.Center,
            )
            IconButton(
                onClick = { /*TODO*/ },
                content = { Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null) },
            )
        }
        Divider()
        Column {
            Card(modifier = Modifier.padding(16.dp)) {
                Text(text = "Total: ${state.total}")
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

    @Preview
    @Composable
    private fun StatsScreenContentDaily_Preview() {
        PerformanceTheme {
            Scaffold {
                Box(modifier = Modifier.padding(it)) {
                    StatsScreenContentDaily(
                        state = StatsViewModel.State.Daily(
                            date = "2020-02-02",
                            total = Seconds(0),
                        ),
                    )
                }
            }
        }
    }