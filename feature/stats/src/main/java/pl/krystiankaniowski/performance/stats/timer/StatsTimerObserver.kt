package pl.krystiankaniowski.performance.stats.timer

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.domain.stats.SaveFocusUseCase
import pl.krystiankaniowski.performance.domain.timer.GetCancelThresholdUseCase
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.domain.timer.TimerObserverPriority
import pl.krystiankaniowski.performance.domain.timer.fits
import pl.krystiankaniowski.performance.model.Focus
import pl.krystiankaniowski.performance.model.toSeconds
import javax.inject.Inject

class StatsTimerObserver @Inject constructor(
    private val clock: Clock,
    private val saveFocusUseCase: SaveFocusUseCase,
    private val getCancelThresholdUseCase: GetCancelThresholdUseCase,
) : TimerObserver {

    private var startDate: Instant? = null

    override val priority: Int = TimerObserverPriority.STATISTICS

    override suspend fun onStart() {
        startDate = clock.now()
    }

    override suspend fun onStop(isInterrupted: Boolean) {
        val startDate = requireNotNull(this.startDate)
        val endDate = clock.now()
        val diff = (endDate - startDate).toSeconds()
        if (!isInterrupted || !getCancelThresholdUseCase.fits(diff)) {
            saveFocusUseCase(
                Focus(
                    startDate = startDate,
                    endDate = endDate,
                ),
            )
        }
        this.startDate = null
    }
}