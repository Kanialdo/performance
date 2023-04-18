package pl.krystiankaniowski.performance.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object PerformanceFormItems {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DateInput(
        label: String,
        date: LocalDate?,
        onDateChange: (LocalDate?) -> Unit,
    ) {

        val openDialog = remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState()

        if (openDialog.value) {
            DatePickerDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            onDateChange(
                                datePickerState.selectedDateMillis?.let {
                                    Instant
                                        .fromEpochMilliseconds(it)
                                        .toLocalDateTime(TimeZone.currentSystemDefault())
                                        .date
                                },
                            )
                        },
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        },
                    ) {
                        Text("Cancel")
                    }
                },
            ) {
                DatePicker(state = datePickerState)
            }
        }

        ListItem(
            modifier = Modifier.clickable { openDialog.value = true },
            headlineText = { Text(text = label) },
            supportingText = { Text(text = date?.toString() ?: "") },
        )
    }
}