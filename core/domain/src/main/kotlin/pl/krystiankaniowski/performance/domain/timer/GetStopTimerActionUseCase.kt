package pl.krystiankaniowski.performance.domain.timer

import pl.krystiankaniowski.performance.model.Seconds

interface GetStopTimerActionUseCase {

    suspend operator fun invoke(): Action

    sealed class Action {
        object GiveUp : Action()
        data class Cancel(val secondsLeft: Seconds) : Action()
    }
}