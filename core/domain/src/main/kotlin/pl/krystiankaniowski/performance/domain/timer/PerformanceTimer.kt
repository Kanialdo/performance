package pl.krystiankaniowski.performance.domain.timer

import kotlinx.coroutines.flow.Flow
import pl.krystiankaniowski.performance.model.Seconds
import pl.krystiankaniowski.performance.model.Tag

interface PerformanceTimer {

    val state: Flow<State>

    fun start(tag: Tag, seconds: Seconds)

    // fun setTag(tag: Tag)

    fun stop()

    sealed interface State {
        object NotStarted : State
        data class Pending(
            val elapsedSeconds: Seconds,
            val leftSeconds: Seconds,
            val tag: Tag,
        ) : State
    }
}