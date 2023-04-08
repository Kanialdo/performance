package pl.krystiankaniowski.performance.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.domain.localization.time.DateTimeFormatter
import pl.krystiankaniowski.performance.domain.localization.time.DurationFormatter
import pl.krystiankaniowski.performance.domain.stats.GetFocusListUseCase
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val getFocusListUseCase: GetFocusListUseCase,
    private val dateTimeFormatter: DateTimeFormatter,
    private val durationFormatter: DurationFormatter,
) : ViewModel() {

    private var reloadJob: Job? = null

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effects: SharedFlow<Effect> = _effects

    fun onEvent(event: Event) = when (event) {
        Event.Refresh -> loadData()
    }

    init {
        loadData()
    }

    private fun loadData() {
        reloadJob?.cancel()
        reloadJob = viewModelScope.launch {
            getFocusListUseCase().collect { items ->
                _state.value = State.Loaded(
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

    fun onItemClick(id: Long) {
        viewModelScope.launch {
            _effects.emit(Effect.OpenDetails(id))
        }
    }

    sealed interface State {
        object Loading : State
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
        object Refresh : Event
    }

    sealed interface Effect {
        data class OpenDetails(val id: Long) : Effect
    }
}