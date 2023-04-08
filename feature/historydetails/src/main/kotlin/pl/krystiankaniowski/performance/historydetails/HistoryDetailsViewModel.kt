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
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import pl.krystiankaniowski.performance.domain.stats.GetHistoryEntryUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryDetailsViewModel @Inject constructor(
    private val getHistoryEntryUseCase: GetHistoryEntryUseCase,
    private val repository: FocusRepository,
    private val dateTimeFormatter: DateTimeFormatter,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id: Long = checkNotNull(savedStateHandle[HistoryDetailsArgs.id])

    sealed interface State {
        object Loading : State
        data class Loaded(
            val startDate: String,
            val endDate: String,
        ) : State
    }

    sealed interface Effect {
        object ShowConfirmationPopup : Effect
        object CloseScreen : Effect
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effects: SharedFlow<Effect> = _effects

    init {
        viewModelScope.launch {
            _state.value = getHistoryEntryUseCase(id).let { data ->
                State.Loaded(
                    startDate = dateTimeFormatter.formatDateTime(data.startDate),
                    endDate = dateTimeFormatter.formatDateTime(data.endDate),
                )
            }
        }
    }

    fun onDeleteButtonClick() = viewModelScope.launch {
        _effects.emit(Effect.ShowConfirmationPopup)
    }

    fun onDeleteConfirmation() = viewModelScope.launch {
        repository.delete(id)
        _effects.emit(Effect.CloseScreen)
    }
}