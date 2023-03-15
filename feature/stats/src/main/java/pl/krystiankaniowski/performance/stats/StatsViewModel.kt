package pl.krystiankaniowski.performance.stats

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase
import pl.krystiankaniowski.performance.ui.arch.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    val getFocusListUseCase: GetFocusListUseCase,
) : BaseViewModel<StatsViewModel.State, Nothing>() {

    private var reloadJob: Job? = null

    override fun initState() = State.Loading

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
                items = getFocusListUseCase().map {
                    State.Loaded.FocusEntry(
                        startDate = it.startDate.toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
                        endDate = it.endDate.toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
                    )
                },
            )
        }
    }

    sealed interface State {
        object Loading : State
        data class Loaded(
            val items: List<FocusEntry>,
        ) : State {
            data class FocusEntry(
                val startDate: String,
                val endDate: String,
            )
        }
    }

    sealed interface Event {
        object Refresh : Event
    }

    sealed class Effect
}