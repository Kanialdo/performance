package pl.krystiankaniowski.performance.addhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

@HiltViewModel
class AddHistoryViewModel @Inject constructor(
    private val repository: FocusRepository,
) : ViewModel() {

    data class State(
        val startDate: LocalDate? = null,
        val startTime: LocalTime? = null,
        val endDate: LocalDate? = null,
        val endTime: LocalTime? = null,
    ) {
        val isSaveButtonEnable: Boolean = startDate != null && endDate != null && startDate < endDate
    }

    sealed interface Effect {
        object CloseScreen : Effect
    }

    sealed interface Event {
        data class StartDateChange(val startDate: LocalDate?) : Event
        data class StartTimeChange(val startTime: LocalTime?) : Event
        data class EndDateChange(val endDate: LocalDate?) : Event
        data class EndTimeChange(val endTime: LocalTime?) : Event
        object OnSaveClick : Event
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effects: SharedFlow<Effect> = _effects

    fun onEvent(event: Event) = when (event) {
        is Event.EndDateChange -> onStartDateChange(event.endDate)
        is Event.EndTimeChange -> TODO()
        Event.OnSaveClick -> onSaveButtonClick()
        is Event.StartDateChange -> onStartDateChange(event.startDate)
        is Event.StartTimeChange -> TODO()
    }

    private fun onStartDateChange(date: LocalDate?) = viewModelScope.launch {
        // verify date
        _state.value = state.value.copy(
            startDate = date,
        )
    }

    private fun onStartTimeChange(time: LocalTime?) = viewModelScope.launch {
        // verify date
        _state.value = state.value.copy(
            startTime = time,
        )
    }

    private fun onEndDateChange(date: LocalDate?) = viewModelScope.launch {
        // verify date
        _state.value = state.value.copy(
            endDate = date,
        )
    }

    private fun onEndTimeChange(time: LocalTime?) = viewModelScope.launch {
        // verify date
        _state.value = state.value.copy(
            endTime = time,
        )
    }

    private fun onSaveButtonClick() = viewModelScope.launch {
        with(state.value) {
            check(isSaveButtonEnable)
            checkNotNull(startDate)
            checkNotNull(startTime)
            checkNotNull(endDate)
            checkNotNull(endTime)
            val focus = Focus(
                startDate = LocalDateTime(startDate, startTime).toInstant(TimeZone.currentSystemDefault()),
                endDate = LocalDateTime(endDate, endTime).toInstant(TimeZone.currentSystemDefault()),
            )
            repository.add(focus)
            _effects.emit(Effect.CloseScreen)
        }
    }
}