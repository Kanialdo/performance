package pl.krystiankaniowski.performance.timer

import kotlinx.coroutines.flow.first
import pl.krystiankaniowski.performance.domain.timer.GetStopTimerActionUseCase
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject

class GetStopTimerActionUseCaseImpl @Inject constructor(
    private val timer: PerformanceTimer,
) : GetStopTimerActionUseCase {
    override suspend fun invoke(): GetStopTimerActionUseCase.Action = when (val state = timer.state.first()) {
        PerformanceTimer.State.NotStarted -> error("TODO")
        is PerformanceTimer.State.Pending -> state.elapsedSeconds.value.let { seconds ->
            if (seconds < 15) {
                GetStopTimerActionUseCase.Action.Cancel(Seconds(15 - seconds))
            } else {
                GetStopTimerActionUseCase.Action.GiveUp
            }
        }
    }
}