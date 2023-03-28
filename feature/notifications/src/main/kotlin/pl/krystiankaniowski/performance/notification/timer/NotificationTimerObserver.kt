package pl.krystiankaniowski.performance.notification.timer

import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.domain.timer.TimerObserverPriority
import pl.krystiankaniowski.performance.domain.usecase.notification.StartForegroundServiceUseCase
import pl.krystiankaniowski.performance.domain.usecase.notification.StopForegroundServiceUseCase
import javax.inject.Inject

class NotificationTimerObserver @Inject constructor(
    private val startForegroundServiceUseCase: StartForegroundServiceUseCase,
    private val stopForegroundServiceUseCase: StopForegroundServiceUseCase,
) : TimerObserver {

    override val priority: Int = TimerObserverPriority.NOTIFICATION

    override suspend fun onStart() {
        startForegroundServiceUseCase()
    }

    override suspend fun onStop() {
        stopForegroundServiceUseCase()
    }
}