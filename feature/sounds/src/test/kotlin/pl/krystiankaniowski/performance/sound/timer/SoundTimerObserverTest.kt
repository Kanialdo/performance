package pl.krystiankaniowski.performance.sound.timer

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.krystiankaniowski.performance.sound.usecase.IsSoundEnabledUseCase
import pl.krystiankaniowski.performance.sound.usecase.PlayNotificationSoundUseCase

class SoundTimerObserverTest {

    private val playNotificationSoundUseCase: PlayNotificationSoundUseCase = mockk()
    private val isSoundEnabledUseCase: IsSoundEnabledUseCase = mockk()

    @Test
    fun `WHEN sounds is enabled THEN play sound on timer start`() = runTest {
        val sut = createSut(isSoundEnabled = true)

        sut.onStart()

        verify { playNotificationSoundUseCase.invoke() }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `WHEN sounds is enabled THEN play sound on timer end`(isInterrupted: Boolean) = runTest {
        val sut = createSut(isSoundEnabled = true)

        sut.onStop(isInterrupted = isInterrupted)

        verify { playNotificationSoundUseCase.invoke() }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `WHEN sounds is not enabled THEN do not play sound on timer start or timer end`(isInterrupted: Boolean) = runTest {
        val sut = createSut(isSoundEnabled = false)

        sut.onStart()
        sut.onStop(isInterrupted = isInterrupted)

        verify(exactly = 0) { playNotificationSoundUseCase.invoke() }
    }

    private fun createSut(isSoundEnabled: Boolean): SoundTimerObserver {
        coEvery { isSoundEnabledUseCase.invoke() } returns isSoundEnabled
        coEvery { playNotificationSoundUseCase.invoke() } just Runs
        return SoundTimerObserver(
            playNotificationSoundUseCase = playNotificationSoundUseCase,
            isSoundEnabledUseCase = isSoundEnabledUseCase,
        )
    }
}