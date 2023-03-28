package pl.krystiankaniowski.performance.timer

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase
import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerformanceTimerImpl @Inject constructor(
    private val saveFocusUseCase: SaveFocusUseCase,
    private val observers: List<@JvmSuppressWildcards TimerObserver>,
) : PerformanceTimer {

    private val scope = MainScope()

    private val _state = MutableSharedFlow<PerformanceTimer.State>(extraBufferCapacity = 1)
    override val state: Flow<PerformanceTimer.State> = _state

    private var job: Job? = null

    private var startDate: Instant? = null

    init {
        scope.launch {
            _state.emit(PerformanceTimer.State.NotStarted)
        }
    }

    override fun start(seconds: Seconds) {
        job = scope.launch {
            startDate = Clock.System.now()
            observers.sortedBy { it.priority }.forEach { it.onStart() }
            (seconds.value - 1 downTo 0)
                .asFlow()
                .onEach { delay(1000) }
                .map { Seconds(it) }
                .onStart { emit(seconds) }
                .conflate()
                .onCompletion {
                    onCompletion()
                }
                .collect {
                    _state.emit(
                        PerformanceTimer.State.Pending(
                            elapsedSeconds = seconds - it,
                            leftSeconds = it,
                        ),
                    )
                }
        }
    }

    override fun stop() {
        job?.cancel(CancellationException())
    }

    private fun onCompletion() {
        scope.launch {
            observers.sortedByDescending { it.priority }.forEach { it.onStop() }
            startDate = null
            _state.emit(PerformanceTimer.State.NotStarted)
        }
    }
}