package pl.krystiankaniowski.performance.vibration.usecase

import android.os.Vibrator
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class IsVibratorAvailableUseCaseTest {

    @Test
    fun `WHEN vibrator is null THEN return false`() = runTest {
        val sut = createSut(vibrator = null)

        val result = sut.invoke()

        Assertions.assertFalse(result)
    }

    @Test
    fun `WHEN vibrator is not null THEN return true`() = runTest {
        val sut = createSut(vibrator = mockk())

        val result = sut.invoke()

        Assertions.assertTrue(result)
    }

    private fun createSut(vibrator: Vibrator?) = IsVibratorAvailableUseCase(vibrator)
}