package pl.krystiankaniowski.performance.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItems
import pl.krystiankaniowski.performance.ui.components.PerformanceLoadingScreen
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
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
            when (val state = viewModel.state.collectAsState().value) {
                is SettingsViewModel.State.Loaded -> SettingsScreenContent(state)
                SettingsViewModel.State.Loading -> PerformanceLoadingScreen()
            }
        }
    }
}

@Composable
private fun SettingsScreenContent(
    state: SettingsViewModel.State.Loaded,
) {
    LazyColumn {
        state.items.forEach {
            item {
                SettingsScreenListItem_Header(
                    title = when (it.key) {
                        SettingsItems.Category.DND -> stringResource(R.string.settings_header_do_not_disturb)
                        SettingsItems.Category.NOTIFICATIONS -> stringResource(R.string.settings_header_notifications)
                        SettingsItems.Category.STATS -> stringResource(R.string.settings_header_statistics)
                        SettingsItems.Category.OTHERS -> stringResource(R.string.settings_header_others)
                        SettingsItems.Category.ABOUT -> stringResource(R.string.settings_header_about)
                    },
                )
            }
            items(it.value) {
                when (it) {
                    is SettingsItem.Simple -> SettingsScreenListItem_Simple(it)
                    is SettingsItem.Switch -> SettingsScreenListItem_Switch(it)
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenContentPreview() {
    PerformanceTheme {
        SettingsScreenContent(
            SettingsViewModel.State.Loaded(
                emptyMap(),
            ),
        )
    }
}