package pl.krystiankaniowski.performance.vibration.timer

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import pl.krystiankaniowski.performance.vibration.usecase.IsVibrationEnabledUseCase
import pl.krystiankaniowski.performance.vibration.usecase.VibrateUseCase

class VibrationTimerObserverTest {

    private val vibrateUseCase: VibrateUseCase = mockk()
    private val isVibrationEnabledUseCase: IsVibrationEnabledUseCase = mockk()

    @Test
    fun `WHEN vibration is enabled THEN vibrate on timer start`() = runTest {
        val sut = createSut(isSoundEnabled = true)

        sut.onStart()

        verify { vibrateUseCase.invoke() }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `WHEN vibration is enabled THEN vibrate on timer end`(isInterrupted: Boolean) = runTest {
        val sut = createSut(isSoundEnabled = true)

        sut.onStop(isInterrupted = isInterrupted)

        verify { vibrateUseCase.invoke() }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `WHEN vibration is not enabled THEN do not vibrate on timer start or timer end`(isInterrupted: Boolean) = runTest {
        val sut = createSut(isSoundEnabled = false)

        sut.onStart()
        sut.onStop(isInterrupted = isInterrupted)

        verify(exactly = 0) { vibrateUseCase.invoke() }
    }

    private fun createSut(isSoundEnabled: Boolean): VibrationTimerObserver {
        coEvery { isVibrationEnabledUseCase.invoke() } returns isSoundEnabled
        coEvery { vibrateUseCase.invoke() } just Runs
        return VibrationTimerObserver(
            vibrateUseCase = vibrateUseCase,
            isVibrationEnabledUseCase = isVibrationEnabledUseCase,
        )
    }
}
