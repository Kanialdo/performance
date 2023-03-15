package pl.krystiankaniowski.performance.settings

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
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
            SettingsScreenContent(viewModel.state.collectAsState().value)
        }
    }
}

@Composable
fun SettingsScreenContent(state: SettingsViewModel.State) {
    LazyColumn {
        item {

            ListItem(
                headlineText = { Text(stringResource(R.string.do_not_disturbed)) },
                supportingText = { Text(text = stringResource(R.string.turn_on_do_not_disturbed_in_focus_time)) },
                trailingContent = {
                    val interactionSource = remember { MutableInteractionSource() }
                    Switch(
                        checked = true,
                        onCheckedChange = {},
                        thumbContent = null,
                        enabled = true,
                        interactionSource = interactionSource,
//                        colors = colors
                    )
                }
            )
            Divider()
            ListItem(
                headlineText = { Text(stringResource(R.string.title_app_version)) },
                supportingText = { Text(text = state.appVersion) },
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenContentPreview() {
    PerformanceTheme {
        SettingsScreenContent(
            SettingsViewModel.State(
                appVersion = "1.0.0",
            ),
        )
    }
}