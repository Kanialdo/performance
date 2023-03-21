package pl.krystiankaniowski.performance.timer

import javax.inject.Inject

class GetTimerUseCase @Inject constructor(
    private val performanceTimer: PerformanceTimer,
) {
    operator fun invoke(): PerformanceTimer = performanceTimer
}