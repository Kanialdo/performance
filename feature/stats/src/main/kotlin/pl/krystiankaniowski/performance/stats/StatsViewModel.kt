package pl.krystiankaniowski.performance.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import pl.krystiankaniowski.performance.domain.localization.time.DurationFormatter
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import pl.krystiankaniowski.performance.model.toSeconds
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: FocusRepository,
    private val durationFormatter: DurationFormatter,
) : ViewModel() {

    sealed interface State {
        object Loading : State
        data class Daily(
            val date: String,
            val total: String,
            val chartData: List<FocusTime>,
        ) : State
    }

    data class FocusTime(
        val milisStart: Long,
        val milisEnd: Long,
    ) {
        val started: Float = (milisStart / 1.days.inWholeMilliseconds.toFloat())
        val width: Float = ((milisEnd - milisStart) / 1.days.inWholeMilliseconds.toFloat())
    }

    sealed interface Effect

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effects: SharedFlow<Effect> = _effects

    init {
        viewModelScope.launch {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            repository.getAll().collect {
                val events = it.filter {
                    it.startDate >= now.date.atStartOfDayIn(TimeZone.currentSystemDefault()) && it.endDate < now.date.plus(DateTimeUnit.DayBased(1))
                        .atStartOfDayIn(TimeZone.currentSystemDefault())
                }
                _state.update {
                    State.Daily(
                        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
                        total = durationFormatter.format(events.sumOf { (it.endDate - it.startDate).inWholeSeconds }.toSeconds()),
                        chartData = events.map {
                            FocusTime(
                                it.startDate.toLocalDateTime(TimeZone.currentSystemDefault()).time.toMillisecondOfDay().toLong(),
                                it.endDate.toLocalDateTime(TimeZone.currentSystemDefault()).time.toMillisecondOfDay().toLong(),
                            )
                        },
                    )
                }
            }
        }
    }
}