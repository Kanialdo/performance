package pl.krystiankaniowski.performance.timer

import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject

class PerformanceTimerImpl @Inject constructor() : PerformanceTimer {

    private val scope = MainScope()

    private val _state = MutableSharedFlow<PerformanceTimer.State>(extraBufferCapacity = 1)
    override val state: Flow<PerformanceTimer.State> = _state

    private var job: Job? = null

    init {
        scope.launch {
            _state.emit(PerformanceTimer.State.NotStarted)
        }
    }

    override fun start(seconds: Seconds) {
        job = scope.launch {
            (seconds.value - 1 downTo 0)
                .asFlow() // Emit total - 1 because the first was emitted onStart
                .onEach { delay(1000) } // Each second later emit a number
                .map { Seconds(it) }
                .onStart { emit(seconds) } // Emit total seconds immediately
                .conflate() // In case the creating of State takes some time, conflate keeps the time ticking separately
                .collect {
                    _state.emit(
                        PerformanceTimer.State.Pending(
                            elapsedSeconds = it,
                            leftSeconds = seconds - it,
                        ),
                    )
                }
        }
    }

    override fun stop() {
        job?.cancel()
        scope.launch {
            _state.emit(PerformanceTimer.State.NotStarted)
        }
    }

}