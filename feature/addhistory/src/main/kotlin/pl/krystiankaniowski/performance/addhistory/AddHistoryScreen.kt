package pl.krystiankaniowski.performance.addhistory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
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
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import pl.krystiankaniowski.performance.adddetails.R
import pl.krystiankaniowski.performance.ui.components.PerformanceFormItems
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme
import pl.krystiankaniowski.performance.ui.utils.collectAsEffect

@Composable
fun AddDetailsScreen(
    viewModel: AddHistoryViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {

    viewModel.effects.collectAsEffect {
        when (it) {
            AddHistoryViewModel.Effect.CloseScreen -> {
                navigateUp()
            }
        }
    }

    AddDetailsContent(
        state = viewModel.state.collectAsState().value,
        navigateUp = navigateUp,
        onSaveButtonClicked = viewModel::onSaveButtonClick,
        onStartDateChanged = viewModel::onStartDateChange,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddDetailsContent(
    state: AddHistoryViewModel.State,
    navigateUp: () -> Unit,
    onSaveButtonClicked: () -> Unit,
    onStartDateChanged: (LocalDate?) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_event_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = navigateUp,
                        content = { Icon(Icons.Default.ArrowBack, null) },
                    )
                },
                actions = {
                    IconButton(
                        content = {
                            Icon(
                                Icons.Default.Done,
                                stringResource(R.string.action_save),
                            )
                        },
                        enabled = state.isSaveButtonEnable,
                        onClick = onSaveButtonClicked,
                    )
                },
            )
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            PerformanceFormItems.DateInput(
                label = stringResource(R.string.add_event_start_label),
                date = state.startDate?.date,
                onDateChange = onStartDateChanged,
            )
            ListItem(
                headlineText = { Text(stringResource(R.string.add_event_end_label)) },
                supportingText = { Text(state.endDate.toString()) },
            )
        }
    }
}

@Preview
@Composable
private fun AddDetailsContent_Preview() {
    PerformanceTheme {
        AddDetailsContent(
            state = AddHistoryViewModel.State(
                startDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                endDate = null,
            ),
            navigateUp = {},
            onSaveButtonClicked = {},
            onStartDateChanged = {},
        )
    }
}