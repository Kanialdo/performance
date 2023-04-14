package pl.krystiankaniowski.performance.vibration.timer

import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.domain.timer.TimerObserverPriority
import pl.krystiankaniowski.performance.vibration.usecase.IsVibrationEnabledUseCase
import pl.krystiankaniowski.performance.vibration.usecase.VibrateUseCase
import javax.inject.Inject
class VibrationTimerObserver @Inject constructor(
    private val isVibrationEnabledUseCase: IsVibrationEnabledUseCase,
    private val vibrateUseCase: VibrateUseCase,
) : TimerObserver {

    override val priority: Int = TimerObserverPriority.VIBRATION

    override suspend fun onStart() {
        if (isVibrationEnabledUseCase()) {
            vibrateUseCase()
        }
    }

    override suspend fun onStop(isInterrupted: Boolean) {
        if (isVibrationEnabledUseCase()) {
            vibrateUseCase()
        }
    }
}