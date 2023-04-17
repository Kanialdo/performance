package pl.krystiankaniowski.performance.addhistory

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddDetailsScreen(
    viewModel: AddHistoryViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
//
//    var showConfirmationPopup by rememberSaveable { mutableStateOf(false) }
//
//    viewModel.effects.collectAsEffect {
//        when (it) {
//            HistoryDetailsViewModel.Effect.ShowConfirmationPopup -> showConfirmationPopup = true
//            HistoryDetailsViewModel.Effect.CloseScreen -> {
//                navigateUp()
//            }
//        }
//    }
//
//    HistoryDetailsContent(
//        state = viewModel.state.collectAsState().value,
//        navigateUp = navigateUp,
//        onDeleteButtonClicked = viewModel::onDeleteButtonClick,
//    )
//
//    if (showConfirmationPopup) {
//        AlertDialog(
//            onDismissRequest = { showConfirmationPopup = false },
//            title = { Text(text = stringResource(R.string.details_confirmation_dialog_title)) },
//            text = { Text(stringResource(R.string.details_confirmation_dialog_description)) },
//            confirmButton = {
//                TextButton(
//                    onClick = {
//                        showConfirmationPopup = false
//                        viewModel.onDeleteConfirmation()
//                    },
//                    content = { Text(stringResource(R.string.details_confirmation_dialog_button_positive_label)) },
//                )
//            },
//            dismissButton = {
//                TextButton(
//                    onClick = { showConfirmationPopup = false },
//                    content = { Text(stringResource(R.string.details_confirmation_dialog_button_negative_label)) },
//                )
//            },
//        )
//    }
}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun HistoryDetailsContent(
//    state: HistoryDetailsViewModel.State,
//    navigateUp: () -> Unit,
//    onDeleteButtonClicked: () -> Unit,
//) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(stringResource(R.string.history_details_title)) },
//                navigationIcon = {
//                    IconButton(
//                        onClick = navigateUp,
//                        content = { Icon(Icons.Default.ArrowBack, null) },
//                    )
//                },
//                actions = {
//                    when (state) {
//                        is HistoryDetailsViewModel.State.Loaded -> {
//                            IconButton(
//                                content = {
//                                    Icon(
//                                        Icons.Outlined.Delete,
//                                        stringResource(R.string.action_delete),
//                                    )
//                                },
//                                onClick = onDeleteButtonClicked,
//                            )
//                        }
//                        else -> Unit
//                    }
//                },
//            )
//        },
//    ) {
//        Column(modifier = Modifier.padding(it)) {
//            when (state) {
//                HistoryDetailsViewModel.State.Loading -> PerformanceLoadingScreen()
//                is HistoryDetailsViewModel.State.Loaded -> HistoryDetailsContentLoaded(
//                    state = state,
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun ColumnScope.HistoryDetailsContentLoaded(state: HistoryDetailsViewModel.State.Loaded) {
//    ListItem(
//        headlineText = { Text(stringResource(R.string.details_start_label)) },
//        supportingText = { Text(state.startDate) },
//    )
//    ListItem(
//        headlineText = { Text(stringResource(R.string.details_end_label)) },
//        supportingText = { Text(state.endDate) },
//    )
//    ListItem(
//        headlineText = { Text(stringResource(R.string.details_duration_label)) },
//        supportingText = { Text(state.duration) },
//    )
//}
//
//@Preview
//@Composable
//private fun HistoryDetailsContent_Loading_Preview() {
//    PerformanceTheme {
//        HistoryDetailsContent(
//            state = HistoryDetailsViewModel.State.Loading,
//            navigateUp = {},
//            onDeleteButtonClicked = {},
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun HistoryDetailsContent_Loaded_Preview() {
//    PerformanceTheme {
//        HistoryDetailsContent(
//            state = HistoryDetailsViewModel.State.Loaded(
//                startDate = "2020-02-02 10:15:20",
//                endDate = "2020-02-02 10:30:20",
//                duration = "25 min",
//            ),
//            navigateUp = {},
//            onDeleteButtonClicked = {},
//        )
//    }
//}