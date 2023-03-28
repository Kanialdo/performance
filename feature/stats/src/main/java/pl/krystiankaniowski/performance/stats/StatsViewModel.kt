package pl.krystiankaniowski.performance.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDateTime
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val getFocusListUseCase: GetFocusListUseCase,
    private val durationTimeFormatter: DurationTimeFormatter,
) : ViewModel() {

    private var reloadJob: Job? = null

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    fun onEvent(event: Event) = when (event) {
        Event.Refresh -> loadData()
    }

    init {
        loadData()
    }

    private fun loadData() {
        reloadJob?.cancel()
        reloadJob = viewModelScope.launch {
            _state.value = State.Loaded(
                items = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Default) {
                    getFocusListUseCase()
                        .groupBy { it.startDate.toLocalDateTime(kotlinx.datetime.TimeZone.Companion.currentSystemDefault()).date }
                        .map {
                            State.Loaded.Item.Header(it.key.toString()) to it.value.sortedByDescending { it.startDate }.map {
                                State.Loaded.Item.Focus(
                                    duration = durationTimeFormatter.format(from = it.startDate, to = it.endDate),
                                )
                            }
                        }
                        .toMap()
                        .toSortedMap { a, b -> b.date.compareTo(a.date) }
                },
            )
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
                    val duration: String,
                ) : Item
            }
        }
    }

    sealed interface Event {
        object Refresh : Event
    }

    sealed class Effect
}