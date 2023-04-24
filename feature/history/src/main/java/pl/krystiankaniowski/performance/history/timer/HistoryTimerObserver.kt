package pl.krystiankaniowski.performance.history.timer

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.domain.stats.SaveFocusUseCase
import pl.krystiankaniowski.performance.domain.timer.GetCancelThresholdUseCase
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.domain.timer.TimerObserverPriority
import pl.krystiankaniowski.performance.domain.timer.fits
import pl.krystiankaniowski.performance.model.Focus
import pl.krystiankaniowski.performance.model.Tag
import pl.krystiankaniowski.performance.model.toSeconds
import javax.inject.Inject

class HistoryTimerObserver @Inject constructor(
    private val clock: Clock,
    private val saveFocusUseCase: SaveFocusUseCase,
    private val getCancelThresholdUseCase: GetCancelThresholdUseCase,
) : TimerObserver {

    private var startDate: Instant? = null
    private var tag: Tag? = null

    override val priority: Int = TimerObserverPriority.STATISTICS

    override suspend fun onStart(tag: Tag) {
        this.tag = tag
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
                    tag = tag,
                ),
            )
        }
        this.startDate = null
        this.tag = null
    }
}