package pl.krystiankaniowski.performance.stats.timer

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.domain.timer.TimerObserverPriority
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

class StatsTimerObserver @Inject constructor(
    private val saveFocusUseCase: SaveFocusUseCase,
) : TimerObserver {

    private var startDate: Instant? = null

    override val priority: Int = TimerObserverPriority.STATS

    override suspend fun onStart() {
        startDate = Clock.System.now()
    }

    override suspend fun onStop() {
        saveFocusUseCase(
            Focus(
                startDate = requireNotNull(startDate),
                endDate = Clock.System.now(),
            ),
        )
        startDate = null
    }
}