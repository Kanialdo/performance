package pl.krystiankaniowski.performance.timer

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.Clock
import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject

class GetFocusCounterUseCase @Inject constructor(
    private val timerDatastore: TimerDatastore,
) {
    operator fun invoke(): Flow<Seconds>? {

        val seconds = timerDatastore.dateOfEnd?.minus(Clock.System.now())?.inWholeSeconds ?: return null

        if (seconds <= 0) {
            return null
        }

        return (seconds - 1 downTo 0)
            .asFlow() // Emit total - 1 because the first was emitted onStart
            .onEach { delay(1000) } // Each second later emit a number
            .map { Seconds(it) }
            .onStart { Seconds(seconds) } // Emit total seconds immediately
            .conflate() // In case the creating of State takes some time, conflate keeps the time ticking separately
    }
}