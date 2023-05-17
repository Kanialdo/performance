package pl.krystiankaniowski.performance.domain.timer

import kotlinx.coroutines.flow.Flow
import pl.krystiankaniowski.performance.model.Seconds

interface PerformanceTimer {

    val state: Flow<State>

    fun start(seconds: Seconds)

    fun stop()

    sealed interface State {
        object NotStarted : State
        data class Pending(
            val elapsedSeconds: Seconds,
            val leftSeconds: Seconds,
        ) : State {
            val progress = elapsedSeconds.value.toFloat() / (elapsedSeconds + leftSeconds).value.toFloat()
        }
    }
}