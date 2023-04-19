package pl.krystiankaniowski.performance.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days
import androidx.compose.material3.TimeInput as MaterialTimeInput

object PerformanceFormItems {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DateInput(
        label: String,
        date: LocalDate?,
        onDateChange: (LocalDate?) -> Unit,
    ) {

        val openDialog = remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = date?.toEpochDays()?.days?.inWholeMilliseconds,
        )

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
            headlineContent = { Text(text = label) },
            supportingContent = { Text(text = date?.toString() ?: "") },
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TimeInput(
        label: String,
        time: LocalTime?,
        onTimeChange: (LocalTime?) -> Unit,
    ) {

        val now by remember {
            mutableStateOf(
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
            )
        }

        val openDialog = remember { mutableStateOf(false) }
        val timePickerState = rememberTimePickerState(
            initialHour = time?.hour ?: now.hour,
            initialMinute = time?.minute ?: now.minute,
            is24Hour = true,
        )

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            onTimeChange(
                                LocalTime(timePickerState.hour, timePickerState.minute),
                            )
                        },
                    ) {
                        Text("OK")
                    }
                },
                text = { MaterialTimeInput(state = timePickerState) },
            )
        }

        ListItem(
            modifier = Modifier.clickable { openDialog.value = true },
            headlineContent = { Text(text = label) },
            supportingContent = { Text(text = time?.toString() ?: "") },
        )
    }
}