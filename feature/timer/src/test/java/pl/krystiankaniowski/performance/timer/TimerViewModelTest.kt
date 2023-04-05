package pl.krystiankaniowski.performance.timer

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.timer.GetCancelThresholdUseCase
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import pl.krystiankaniowski.performance.model.Seconds
import pl.krystiankaniowski.performance.model.toSeconds
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class TimerViewModelTest {

    private val performanceTimer: PerformanceTimer = mockk()
    private val getCancelThresholdUseCase: GetCancelThresholdUseCase = mockk()

    @Test
    fun `WHEN start is requested THEN start is performed on timer`() = runTest {
        coEvery { performanceTimer.start(any()) }.answers { }
        val sut = createSut()

        sut.onEvent(TimerViewModel.Event.Start)

        coVerify { performanceTimer.start(any()) }
    }

    @Test
    fun `WHEN stop is requested THEN stop is performed on timer`() = runTest {
        coEvery { performanceTimer.stop() }.answers { }
        val sut = createSut()

        sut.onEvent(TimerViewModel.Event.Stop)

        coVerify { performanceTimer.stop() }
    }

    @Test
    fun `WHEN cancel is requested THEN stop is performed on timer`() = runTest {
        coEvery { performanceTimer.stop() }.answers { }
        val sut = createSut()

        sut.onEvent(TimerViewModel.Event.Cancel)

        coVerify { performanceTimer.stop() }
    }

    @Test
    fun `WHEN timer is active and cancel is not possible THEN emit active state with stop button`() = runTest {
        coEvery { getCancelThresholdUseCase() } returns Seconds(0)

        val sut = createSut(
            timerState = PerformanceTimer.State.Pending(
                elapsedSeconds = Seconds(10),
                leftSeconds = Seconds(10),
            ),
        )

        Assertions.assertEquals(
            sut.state.first(),
            TimerViewModel.State(
                counter = "0:10",
                isTimerActive = true,
                button = TimerViewModel.State.Button.Stop,
            ),
        )
    }

    @Test
    fun `WHEN timer is active and cancel is possible THEN emit active state with cancel button`() = runTest {
        coEvery { getCancelThresholdUseCase() } returns 10.toSeconds()

        val sut = createSut(
            timerState = PerformanceTimer.State.Pending(
                elapsedSeconds = Seconds(0),
                leftSeconds = Seconds(10),
            ),
        )

        Assertions.assertEquals(
            sut.state.first(),
            TimerViewModel.State(
                counter = "0:10",
                isTimerActive = true,
                button = TimerViewModel.State.Button.Cancel(10.toSeconds()),
            ),
        )
    }

    @Test
    fun `WHEN timer is not active THEN emit not active state`() = runTest {
        val sut = createSut()

        Assertions.assertEquals(
            sut.state.first(),
            TimerViewModel.State(
                counter = "25:00",
                isTimerActive = false,
                button = TimerViewModel.State.Button.Start,
            ),
        )
    }

    private fun createSut(timerState: PerformanceTimer.State = PerformanceTimer.State.NotStarted): TimerViewModel {
        coEvery { performanceTimer.state }.coAnswers { flowOf(timerState) }
        return TimerViewModel(
            timer = performanceTimer,
            getCancelThresholdUseCase = getCancelThresholdUseCase,
        )
    }
}