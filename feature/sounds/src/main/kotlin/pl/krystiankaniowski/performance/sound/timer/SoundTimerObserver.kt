package pl.krystiankaniowski.performance.sound.timer

import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.domain.timer.TimerObserverPriority
import pl.krystiankaniowski.performance.sound.usecase.IsSoundEnabledUseCase
import pl.krystiankaniowski.performance.sound.usecase.PlayNotificationSoundUseCase
import javax.inject.Inject
class SoundTimerObserver @Inject constructor(
    private val isSoundEnabledUseCase: IsSoundEnabledUseCase,
    private val playNotificationSoundUseCase: PlayNotificationSoundUseCase,
) : TimerObserver {

    override val priority: Int = TimerObserverPriority.SOUND

    override suspend fun onStart() {
        if (isSoundEnabledUseCase()) {
            playNotificationSoundUseCase()
        }
    }

    override suspend fun onStop(isInterrupted: Boolean) {
        if (isSoundEnabledUseCase()) {
            playNotificationSoundUseCase()
        }
    }
}