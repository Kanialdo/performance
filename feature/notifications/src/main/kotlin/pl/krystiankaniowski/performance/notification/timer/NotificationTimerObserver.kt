package pl.krystiankaniowski.performance.notification.timer

import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.domain.timer.TimerObserverPriority
import pl.krystiankaniowski.performance.model.Tag
import pl.krystiankaniowski.performance.notification.usecase.StartForegroundServiceUseCase
import pl.krystiankaniowski.performance.notification.usecase.StopForegroundServiceUseCase
import javax.inject.Inject

class NotificationTimerObserver @Inject constructor(
    private val startForegroundServiceUseCase: StartForegroundServiceUseCase,
    private val stopForegroundServiceUseCase: StopForegroundServiceUseCase,
) : TimerObserver {

    override val priority: Int = TimerObserverPriority.FOREGROUND_NOTIFICATION

    override suspend fun onStart(tag: Tag) {
        startForegroundServiceUseCase()
    }

    override suspend fun onStop(isInterrupted: Boolean) {
        stopForegroundServiceUseCase()
    }
}