package pl.krystiankaniowski.performance.dnd.timer

import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.domain.usecase.dnd.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOffDoNotDisturbUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOnDoNotDisturbUseCase
import javax.inject.Inject

class DndTimerObserver @Inject constructor(
    private val isDoNotDisturbEnabledUseCase: IsDoNotDisturbEnabledUseCase,
    private val turnOnDoNotDisturbUseCase: TurnOnDoNotDisturbUseCase,
    private val turnOffDoNotDisturbUseCase: TurnOffDoNotDisturbUseCase,
) : TimerObserver {

    override val priority: Int
        get() = TODO("Not yet implemented")

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