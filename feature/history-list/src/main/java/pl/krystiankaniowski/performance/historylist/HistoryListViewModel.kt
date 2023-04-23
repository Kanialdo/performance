package pl.krystiankaniowski.performance.historylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.architecture.ViewModelState
import pl.krystiankaniowski.performance.domain.localization.time.DateTimeFormatter
import pl.krystiankaniowski.performance.domain.localization.time.DurationFormatter
import pl.krystiankaniowski.performance.domain.stats.GetFocusListUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryListViewModel @Inject constructor(
    private val getFocusListUseCase: GetFocusListUseCase,
    private val dateTimeFormatter: DateTimeFormatter,
    private val durationFormatter: DurationFormatter,
) : ViewModel() {

    private val _state: ViewModelState<State> = ViewModelState(scope = viewModelScope, initState = State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effects: SharedFlow<Effect> = _effects

    fun onEvent(event: Event) = viewModelScope.launch {
        when (event) {
            Event.OnAddItemClick -> _effects.emit(Effect.OpenAddItem)
        }
    }

    init {
        viewModelScope.launch {
            getFocusListUseCase().collect { items ->
                _state.update {
                    when {
                        items.isEmpty() -> State.Empty
                        else -> State.Loaded(
                            items = items
                                .groupBy { dateTimeFormatter.formatDate(it.startDate) }
                                .map {
                                    State.Loaded.Item.Header(it.key) to it.value.sortedByDescending { it.startDate }.map {
                                        State.Loaded.Item.Focus(
                                            id = it.id,
                                            duration = durationFormatter.format(from = it.startDate, to = it.endDate),
                                        )
                                    }
                                }
                                .toMap()
                                .toSortedMap { a, b -> b.date.compareTo(a.date) },
                        )
                    }
                }
            }
        }
    }

    fun onItemClick(id: Long) {
        viewModelScope.launch {
            _effects.emit(Effect.OpenDetails(id))
        }
    }

    sealed interface State {
        object Loading : State
        object Empty : State
        data class Loaded(
            val items: Map<Item.Header, List<Item.Focus>>,
        ) : State {
            sealed interface Item {
                data class Header(val date: String) : Item
                data class Focus(
                    val id: Long,
                    val duration: String,
                ) : Item
            }
        }
    }

    sealed interface Event {
        object OnAddItemClick : Event
    }

    sealed interface Effect {
        object OpenAddItem : Effect
        data class OpenDetails(val id: Long) : Effect
    }
}