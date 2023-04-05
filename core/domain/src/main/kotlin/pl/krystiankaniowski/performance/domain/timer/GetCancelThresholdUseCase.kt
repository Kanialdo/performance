package pl.krystiankaniowski.performance.domain.timer

import pl.krystiankaniowski.performance.model.Seconds

interface GetCancelThresholdUseCase {

    operator fun invoke(): Seconds
}

fun GetCancelThresholdUseCase.fits(elapsed: Seconds): Boolean = invoke() > elapsed

fun GetCancelThresholdUseCase.fits(state: PerformanceTimer.State.Pending) = invoke() > state.elapsedSeconds

fun GetCancelThresholdUseCase.left(elapsed: Seconds): Seconds = invoke() - elapsed

fun GetCancelThresholdUseCase.left(state: PerformanceTimer.State.Pending): Seconds = invoke() - state.elapsedSeconds