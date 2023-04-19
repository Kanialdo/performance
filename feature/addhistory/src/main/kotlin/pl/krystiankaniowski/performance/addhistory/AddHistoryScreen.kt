package pl.krystiankaniowski.performance.addhistory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
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
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddDetailsContent(
    state: AddHistoryViewModel.State,
    navigateUp: () -> Unit,
    onEvent: (AddHistoryViewModel.Event) -> Unit,
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
                        onClick = { onEvent(AddHistoryViewModel.Event.OnSaveClick) },
                    )
                },
            )
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            PerformanceFormItems.DateInput(
                label = stringResource(R.string.add_event_start_label),
                date = state.startDate,
                onDateChange = { date -> onEvent(AddHistoryViewModel.Event.StartDateChange(date)) },
            )
            PerformanceFormItems.TimeInput(
                label = stringResource(R.string.add_event_start_time_label),
                time = state.startTime,
                onTimeChange = { time -> onEvent(AddHistoryViewModel.Event.StartTimeChange(time)) },
            )
            PerformanceFormItems.DateInput(
                label = stringResource(R.string.add_event_end_label),
                date = state.endDate,
                onDateChange = { date -> onEvent(AddHistoryViewModel.Event.EndDateChange(date)) },
            )
            PerformanceFormItems.TimeInput(
                label = stringResource(R.string.add_event_end_time_label),
                time = state.endTime,
                onTimeChange = { time -> onEvent(AddHistoryViewModel.Event.EndTimeChange(time)) },
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
                startDate = LocalDate(2020, 10, 20),
                startTime = LocalTime(8, 30),
                endDate = null,
            ),
            navigateUp = {},
            onEvent = {},
        )
    }
}