package pl.krystiankaniowski.performance.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
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

    interface State {
        object Loading : State
        object Error : State
        data class Daily(
            val date: String,
            val total: String,
            val chartData: List<FocusTime>,
            val isPreviousButtonEnabled: Boolean,
            val isNextButtonEnabled: Boolean,
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

    private var currentDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    private var job: Job? = null

    init {
        collectStats()
    }

    private fun collectStats() {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                repository.getAll(
                    from = currentDate.atTime(LocalTime(0, 0, 0)).toInstant(TimeZone.currentSystemDefault()),
                    to = currentDate.atTime(LocalTime(23, 59, 59)).toInstant(TimeZone.currentSystemDefault()),
                ).collect { focuses ->
                    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
                    _state.update {
                        State.Daily(
                            date = dateTimeFormatter.formatDate(currentDate),
                            total = durationFormatter.format(focuses.sumOf { (it.endDate - it.startDate).inWholeSeconds }.toSeconds()),
                            chartData = focuses.map {
                                FocusTime(
                                    it.startDate.toLocalDateTime(TimeZone.currentSystemDefault()).time.toMillisecondOfDay().toLong(),
                                    it.endDate.toLocalDateTime(TimeZone.currentSystemDefault()).time.toMillisecondOfDay().toLong(),
                                )
                            },
                            isPreviousButtonEnabled = (now - currentDate).days < MAX_HISTORY_DAYS,
                            isNextButtonEnabled = currentDate < now,
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.update { State.Error }
            }
        }
    }

    fun onPreviousClick() {
        currentDate = currentDate.minus(1, DateTimeUnit.DAY)
        collectStats()
    }

    fun onNextClick() {
        currentDate = currentDate.plus(1, DateTimeUnit.DAY)
        collectStats()
    }

    companion object {
        const val MAX_HISTORY_DAYS = 14
    }
}