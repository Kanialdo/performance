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
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

@HiltViewModel
class AddHistoryViewModel @Inject constructor(
    private val repository: FocusRepository,
) : ViewModel() {

    data class State(
        val startDate: LocalDateTime? = null,
        val endDate: LocalDateTime? = null,
    ) {
        val isSaveButtonEnable: Boolean = startDate != null && endDate != null && startDate < endDate
    }

    sealed interface Effect {
        object CloseScreen : Effect
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effects: SharedFlow<Effect> = _effects

    // TODO: is dateTime ok? shouldn't be instant?
    fun onStartDateChange(dateTime: LocalDate?) = viewModelScope.launch {
        // verify date
        _state.value = state.value.copy(
            startDate = dateTime?.atTime(LocalTime(hour = 0, minute = 0)),
        )
    }

    fun onEndDateChange(dateTime: LocalDateTime) = viewModelScope.launch {
        // verify date
        _state.value = state.value.copy(
            endDate = dateTime,
        )
    }

    fun onSaveButtonClick() = viewModelScope.launch {
        with(state.value) {
            check(isSaveButtonEnable)
            val focus = Focus(
                startDate = startDate?.toInstant(TimeZone.currentSystemDefault()) ?: error("startDate is not set"),
                endDate = endDate?.toInstant(TimeZone.currentSystemDefault()) ?: error("endDate is not set"),
            )
            repository.add(focus)
            _effects.emit(Effect.CloseScreen)
        }
    }
}