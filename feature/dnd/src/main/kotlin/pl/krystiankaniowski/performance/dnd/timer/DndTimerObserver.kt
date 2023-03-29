package pl.krystiankaniowski.performance.dnd.timer

import pl.krystiankaniowski.performance.dnd.usecase.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.dnd.usecase.TurnOffDoNotDisturbUseCase
import pl.krystiankaniowski.performance.dnd.usecase.TurnOnDoNotDisturbUseCase
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.domain.timer.TimerObserverPriority
import javax.inject.Inject

class DndTimerObserver @Inject constructor(
    private val isDoNotDisturbEnabledUseCase: IsDoNotDisturbEnabledUseCase,
    private val turnOnDoNotDisturbUseCase: TurnOnDoNotDisturbUseCase,
    private val turnOffDoNotDisturbUseCase: TurnOffDoNotDisturbUseCase,
) : TimerObserver {

    override val priority: Int = TimerObserverPriority.DO_NOT_DISTURB

    override suspend fun onStart() {
        if (isDoNotDisturbEnabledUseCase()) {
            turnOnDoNotDisturbUseCase()
        }
    }

    override suspend fun onStop() {
        if (isDoNotDisturbEnabledUseCase()) {
            turnOffDoNotDisturbUseCase()
        }
    }
}