package pl.krystiankaniowski.performance.historyadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import pl.krystiankaniowski.performance.architecture.ViewModelState
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

@HiltViewModel
class HistoryAddViewModel @Inject constructor(
    private val repository: FocusRepository,
) : ViewModel() {

    data class State(
        val startDate: LocalDate? = null,
        val startTime: LocalTime? = null,
        val endDate: LocalDate? = null,
        val endTime: LocalTime? = null,
    ) {

        @Suppress("ComplexCondition")
        val isSaveButtonEnable: Boolean =
            if (startDate != null && startTime != null && endDate != null && endTime != null) {
                LocalDateTime(startDate, startTime) < LocalDateTime(endDate, endTime)
            } else {
                false
            }
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

    private val _state: ViewModelState<State> = ViewModelState(scope = viewModelScope, initState = State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effects: SharedFlow<Effect> = _effects

    fun onEvent(event: Event) = when (event) {
        is Event.EndDateChange -> onEndDateChange(event.endDate)
        is Event.EndTimeChange -> onEndTimeChange(event.endTime)
        is Event.StartDateChange -> onStartDateChange(event.startDate)
        is Event.StartTimeChange -> onStartTimeChange(event.startTime)
        Event.OnSaveClick -> onSaveButtonClick()
    }

    private fun onStartDateChange(date: LocalDate?) = _state.update {
        copy(startDate = date)
    }

    private fun onStartTimeChange(time: LocalTime?) = _state.update {
        copy(startTime = time)
    }

    private fun onEndDateChange(date: LocalDate?) = _state.update {
        copy(endDate = date)
    }

    private fun onEndTimeChange(time: LocalTime?) = _state.update {
        copy(endTime = time)
    }

    private fun onSaveButtonClick() = _state.run {
        check(isSaveButtonEnable)
        val focus = Focus(
            startDate = LocalDateTime(checkNotNull(startDate), checkNotNull(startTime)).toInstant(TimeZone.currentSystemDefault()),
            endDate = LocalDateTime(checkNotNull(endDate), checkNotNull(endTime)).toInstant(TimeZone.currentSystemDefault()),
        )
        repository.add(focus)
        _effects.emit(Effect.CloseScreen)
    }
}