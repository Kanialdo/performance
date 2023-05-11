package pl.krystiankaniowski.performance.historyadd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import pl.krystiankaniowski.performance.architecture.runIf
import pl.krystiankaniowski.performance.architecture.transform
import pl.krystiankaniowski.performance.architecture.transformIf
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

@HiltViewModel
class HistoryAddViewModel @Inject constructor(
    private val repository: FocusRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id: Long? = savedStateHandle[HistoryAddEditArgs.id]

    sealed interface State {
        object Loading : State

        data class Loaded(
            val startDate: LocalDate? = null,
            val startTime: LocalTime? = null,
            val endDate: LocalDate? = null,
            val endTime: LocalTime? = null,
        ) : State {

            @Suppress("ComplexCondition")
            val isSaveButtonEnable: Boolean =
                if (startDate != null && startTime != null && endDate != null && endTime != null) {
                    LocalDateTime(startDate, startTime) < LocalDateTime(endDate, endTime)
                } else {
                    false
                }
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

    private val _state = MutableStateFlow(if (id != null) State.Loading else State.Loaded())
    val state: StateFlow<State> = _state

    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effects: SharedFlow<Effect> = _effects

    init {
        if (id != null) {
            _state.transform(viewModelScope) {
                val entry = repository.get(id)
                State.Loaded(
                    startDate = entry.startDate.toLocalDateTime(TimeZone.currentSystemDefault()).date,
                    startTime = entry.startDate.toLocalDateTime(TimeZone.currentSystemDefault()).time,
                    endDate = entry.endDate.toLocalDateTime(TimeZone.currentSystemDefault()).date,
                    endTime = entry.endDate.toLocalDateTime(TimeZone.currentSystemDefault()).time,
                )
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.EndDateChange -> onEndDateChange(event.endDate)
            is Event.EndTimeChange -> onEndTimeChange(event.endTime)
            is Event.StartDateChange -> onStartDateChange(event.startDate)
            is Event.StartTimeChange -> onStartTimeChange(event.startTime)
            Event.OnSaveClick -> onSaveButtonClick()
        }
    }

    private fun onStartDateChange(date: LocalDate?) = _state.transformIf<State.Loaded> {
        copy(startDate = date)
    }

    private fun onStartTimeChange(time: LocalTime?) = _state.transformIf<State.Loaded> {
        copy(startTime = time)
    }

    private fun onEndDateChange(date: LocalDate?) = _state.transformIf<State.Loaded> {
        copy(endDate = date)
    }

    private fun onEndTimeChange(time: LocalTime?) = _state.transformIf<State.Loaded> {
        copy(endTime = time)
    }

    private fun onSaveButtonClick() = _state.runIf<State.Loaded>(viewModelScope) {
        check(isSaveButtonEnable)
        val focus = Focus(
            id = id ?: -1,
            startDate = LocalDateTime(checkNotNull(startDate), checkNotNull(startTime)).toInstant(TimeZone.currentSystemDefault()),
            endDate = LocalDateTime(checkNotNull(endDate), checkNotNull(endTime)).toInstant(TimeZone.currentSystemDefault()),
        )
        if (id != null) {
            repository.update(focus)
        } else {
            repository.insert(focus)
        }
        _effects.emit(Effect.CloseScreen)
    }
}