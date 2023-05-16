package pl.krystiankaniowski.performance.historydetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import pl.krystiankaniowski.performance.ui.components.PerformanceLoadingScreen
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme
import pl.krystiankaniowski.performance.ui.utils.collectAsEffect

@Composable
fun HistoryDetailsScreen(
    viewModel: HistoryDetailsViewModel = hiltViewModel(),
    openEditScreen: (Long) -> Unit,
    navigateUp: () -> Unit,
) {

    var showConfirmationPopup by rememberSaveable { mutableStateOf(false) }

    viewModel.effects.collectAsEffect {
        when (it) {
            is HistoryDetailsViewModel.Effect.OpenEditScreen -> openEditScreen(it.id)
            HistoryDetailsViewModel.Effect.ShowConfirmationPopup -> showConfirmationPopup = true
            HistoryDetailsViewModel.Effect.CloseScreen -> {
                navigateUp()
            }
        }
    }

    HistoryDetailsContent(
        state = viewModel.state.collectAsState().value,
        navigateUp = navigateUp,
        onEditButtonClicked = viewModel::onEditButtonClick,
        onDeleteButtonClicked = viewModel::onDeleteButtonClick,
    )

    if (showConfirmationPopup) {
        AlertDialog(
            onDismissRequest = { showConfirmationPopup = false },
            title = { Text(text = stringResource(R.string.details_confirmation_dialog_title)) },
            text = { Text(stringResource(R.string.details_confirmation_dialog_description)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmationPopup = false
                        viewModel.onDeleteConfirmation()
                    },
                    content = { Text(stringResource(R.string.details_confirmation_dialog_button_positive_label)) },
                )
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmationPopup = false },
                    content = { Text(stringResource(R.string.details_confirmation_dialog_button_negative_label)) },
                )
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryDetailsContent(
    state: HistoryDetailsViewModel.State,
    navigateUp: () -> Unit,
    onEditButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
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
                actions = {
                    when (state) {
                        is HistoryDetailsViewModel.State.Loaded -> {
                            IconButton(
                                content = {
                                    Icon(
                                        Icons.Outlined.Edit,
                                        stringResource(R.string.action_edit),
                                    )
                                },
                                onClick = onEditButtonClicked,
                            )
                            IconButton(
                                content = {
                                    Icon(
                                        Icons.Outlined.Delete,
                                        stringResource(R.string.action_delete),
                                    )
                                },
                                onClick = onDeleteButtonClicked,
                            )
                        }
                        else -> Unit
                    }
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
        headlineContent = { Text(stringResource(R.string.details_start_label)) },
        supportingContent = { Text(state.startDate) },
    )
    ListItem(
        headlineContent = { Text(stringResource(R.string.details_end_label)) },
        supportingContent = { Text(state.endDate) },
    )
    ListItem(
        headlineContent = { Text(stringResource(R.string.details_duration_label)) },
        supportingContent = { Text(state.duration) },
    )
}

@Preview
@Composable
private fun HistoryDetailsContent_Loading_Preview() {
    PerformanceTheme {
        HistoryDetailsContent(
            state = HistoryDetailsViewModel.State.Loading,
            navigateUp = {},
            onEditButtonClicked = {},
            onDeleteButtonClicked = {},
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
                duration = "25 min",
            ),
            navigateUp = {},
            onEditButtonClicked = {},
            onDeleteButtonClicked = {},
        )
    }
}