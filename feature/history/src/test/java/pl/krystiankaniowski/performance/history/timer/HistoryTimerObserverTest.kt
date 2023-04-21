package pl.krystiankaniowski.performance.history.timer

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.jupiter.api.Test
import pl.krystiankaniowski.performance.domain.stats.SaveFocusUseCase
import pl.krystiankaniowski.performance.domain.timer.GetCancelThresholdUseCase
import pl.krystiankaniowski.performance.model.toSeconds
import pl.krystiankaniowski.performance.testing.Virtual
import kotlin.time.Duration.Companion.seconds

class HistoryTimerObserverTest {

    private val clock = Clock.Virtual(Clock.System.now())
    private val saveFocusUseCase: SaveFocusUseCase = mockk()
    private val getCancelThresholdUseCase: GetCancelThresholdUseCase = mockk()

    @Test
    fun `WHEN onStart and onStop without interruption are invoked THEN save session`() = runTest {
        coEvery { saveFocusUseCase.invoke(any()) } answers {}

        val sut = createSut()

        sut.onStart()
        clock.fixedInstant += 60.seconds
        sut.onStop(isInterrupted = false)

        coVerify { saveFocusUseCase.invoke(any()) }
    }

    @Test
    fun `WHEN onStart and onStop with interruption are invoked fitting cancel threshold THEN do not save session`() = runTest {
        coEvery { getCancelThresholdUseCase.invoke() } returns 15.toSeconds()
        coEvery { saveFocusUseCase.invoke(any()) } answers {}

        val sut = createSut()

        sut.onStart()
        clock.fixedInstant += 10.seconds
        sut.onStop(isInterrupted = true)

        coVerify(exactly = 0) { saveFocusUseCase.invoke(any()) }
    }

    @Test
    fun `WHEN onStart and onStop with interruption are invoked not fitting cancel threshold THEN save session`() = runTest {
        coEvery { getCancelThresholdUseCase.invoke() } returns 15.toSeconds()
        coEvery { saveFocusUseCase.invoke(any()) } answers {}

        val sut = createSut()

        sut.onStart()
        clock.fixedInstant += 30.seconds
        sut.onStop(isInterrupted = true)

        coVerify { saveFocusUseCase.invoke(any()) }
    }

    private fun createSut() = HistoryTimerObserver(
        clock = clock,
        saveFocusUseCase = saveFocusUseCase,
        getCancelThresholdUseCase = getCancelThresholdUseCase,
    )
}