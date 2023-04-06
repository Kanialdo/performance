package pl.krystiankaniowski.performance.historydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.domain.usecase.GetHistoryEntryUseCase

class HistoryDetailsViewModel @AssistedInject constructor(
    private val getHistoryEntryUseCase: GetHistoryEntryUseCase,
    @Assisted private val id: Long,
) : ViewModel() {

    sealed interface State {
        object Loading : State
        data class Loaded(
            val startDate: Instant,
            val endDate: Instant,
        ) : State
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            _state.value = getHistoryEntryUseCase(id).let { data ->
                State.Loaded(
                    startDate = data.startDate,
                    endDate = data.endDate,
                )
            }
        }
    }
}