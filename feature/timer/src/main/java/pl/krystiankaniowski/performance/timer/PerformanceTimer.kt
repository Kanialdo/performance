package pl.krystiankaniowski.performance.timer

import kotlinx.coroutines.flow.Flow
import pl.krystiankaniowski.performance.model.Seconds

interface PerformanceTimer {

    val state: Flow<State>

    fun startTimer(seconds: Seconds)

    sealed interface State {
        object Disabled : State
        data class Pending(
            val elapsedSeconds: Seconds,
            val leftSeconds: Seconds,
        ) : State
    }
}