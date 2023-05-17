package pl.krystiankaniowski.performance.timer.usecase

import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import javax.inject.Inject

class GetProgressUseCase @Inject constructor() {

    operator fun invoke(state: PerformanceTimer.State): Float = when (state) {
        PerformanceTimer.State.NotStarted -> 0f
        is PerformanceTimer.State.Pending -> state.progress
    }
}