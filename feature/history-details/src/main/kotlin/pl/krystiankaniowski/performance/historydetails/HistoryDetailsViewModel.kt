package pl.krystiankaniowski.performance.historydetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.architecture.update
import pl.krystiankaniowski.performance.domain.localization.time.DateTimeFormatter
import pl.krystiankaniowski.performance.domain.localization.time.DurationFormatter
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import javax.inject.Inject

@HiltViewModel
class HistoryDetailsViewModel @Inject constructor(
    private val repository: FocusRepository,
    private val dateTimeFormatter: DateTimeFormatter,
    private val durationFormatter: DurationFormatter,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id: Long = checkNotNull(savedStateHandle[HistoryDetailsArgs.id])

    sealed interface State {
        object Loading : State
        object ItemNotExist : State
        data class Loaded(
            val startDate: String,
            val endDate: String,
            val duration: String,
        ) : State
    }

    sealed interface Effect {
        object ShowConfirmationPopup : Effect
        data class OpenEditScreen(val id: Long) : Effect
        object CloseScreen : Effect
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effects: SharedFlow<Effect> = _effects

    init {
        viewModelScope.launch {
            repository.observe(id).collect { data ->
                _state.update(
                    when (data) {
                        null -> State.ItemNotExist
                        else -> State.Loaded(
                            startDate = dateTimeFormatter.formatDateTime(data.startDate),
                            endDate = dateTimeFormatter.formatDateTime(data.endDate),
                            duration = durationFormatter.format(data.duration),
                        )
                    },
                )
            }
        }
    }

    fun onEditButtonClick() = viewModelScope.launch {
        _effects.emit(Effect.OpenEditScreen(id))
    }

    fun onDeleteButtonClick() = viewModelScope.launch {
        _effects.emit(Effect.ShowConfirmationPopup)
    }

    fun onDeleteConfirmation() = viewModelScope.launch {
        repository.delete(id)
        _effects.emit(Effect.CloseScreen)
    }
}