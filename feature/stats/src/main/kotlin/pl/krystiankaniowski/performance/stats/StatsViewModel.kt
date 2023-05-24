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
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import pl.krystiankaniowski.performance.domain.localization.time.DateTimeFormatter
import pl.krystiankaniowski.performance.domain.localization.time.DurationFormatter
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import pl.krystiankaniowski.performance.model.toSeconds
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: FocusRepository,
    private val dateTimeFormatter: DateTimeFormatter,
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
        val millsStart: Long,
        val millsEnd: Long,
    ) {
        val started: Float = (millsStart / 1.days.inWholeMilliseconds.toFloat())
        val width: Float = ((millsEnd - millsStart) / 1.days.inWholeMilliseconds.toFloat())
    }

    sealed interface Effect

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    private val _effects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effects: SharedFlow<Effect> = _effects

    init {
        viewModelScope.launch {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            repository.getAll(
                from = now.date.atTime(LocalTime(0, 0, 0)).toInstant(TimeZone.currentSystemDefault()),
                to = now.date.atTime(LocalTime(23, 59, 59)).toInstant(TimeZone.currentSystemDefault()),
            ).collect { focuses ->
                _state.update {
                    State.Daily(
                        date = dateTimeFormatter.formatDate(now.date),
                        total = durationFormatter.format(focuses.sumOf { (it.endDate - it.startDate).inWholeSeconds }.toSeconds()),
                        chartData = focuses.map {
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