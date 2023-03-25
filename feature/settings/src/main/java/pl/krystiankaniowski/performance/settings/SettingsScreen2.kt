package pl.krystiankaniowski.performance.settings

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.ui.components.PerformanceLoadingScreen
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen2(
    viewModel: SettingsViewModel2 = hiltViewModel(),
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
                is SettingsViewModel2.State.Loaded -> SettingsScreenContent(state)
                SettingsViewModel2.State.Loading -> PerformanceLoadingScreen()
            }
        }
    }
}

@Composable
private fun SettingsScreenContent(
    state: SettingsViewModel2.State.Loaded,
) {
    LazyColumn {
        items(state.items) {
            when (it) {
                is SettingsItem.Switch -> {
                    ListItem(
                        headlineText = { Text(it.title) },
                        supportingText = it.description?.let { { Text(it) } },
                        trailingContent = {
                            val interactionSource = remember { MutableInteractionSource() }
                            Switch(
                                checked = it.value,
                                onCheckedChange = it.onValueChanged,
                                thumbContent = null,
                                enabled = it.isEnabled,
                                interactionSource = interactionSource,
                            )
                        },
                    )
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
            SettingsViewModel2.State.Loaded(
               emptyList()
            ),
        )
    }
}