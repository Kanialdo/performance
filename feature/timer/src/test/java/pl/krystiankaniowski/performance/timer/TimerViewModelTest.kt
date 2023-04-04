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
import pl.krystiankaniowski.performance.domain.timer.GetStopTimerActionUseCase
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import pl.krystiankaniowski.performance.model.Seconds
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class TimerViewModelTest {

    private val performanceTimer: PerformanceTimer = mockk()
    private val getStopTimerActionUseCase: GetStopTimerActionUseCase = mockk()

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
    fun `WHEN cancel is requested THEN cancel is performed on timer`() = runTest {
        coEvery { performanceTimer.stop() }.answers { }
        val sut = createSut()

        sut.onEvent(TimerViewModel.Event.Cancel)

        coVerify { performanceTimer.cancel() }
    }

    @Test
    fun `WHEN timer is active and cancel is not possible THEN emit active state with stop button`() = runTest {
        coEvery { getStopTimerActionUseCase.invoke() } returns GetStopTimerActionUseCase.Action.GiveUp
        coEvery { performanceTimer.state }.coAnswers {
            flowOf(
                PerformanceTimer.State.Pending(
                    elapsedSeconds = Seconds(10),
                    leftSeconds = Seconds(10),
                ),
            )
        }

        val sut = createSut()

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
        coEvery { getStopTimerActionUseCase.invoke() } returns GetStopTimerActionUseCase.Action.Cancel(Seconds(10))
        coEvery { performanceTimer.state }.coAnswers {
            flowOf(
                PerformanceTimer.State.Pending(
                    elapsedSeconds = Seconds(10),
                    leftSeconds = Seconds(10),
                ),
            )
        }

        val sut = createSut()

        Assertions.assertEquals(
            sut.state.first(),
            TimerViewModel.State(
                counter = "0:10",
                isTimerActive = true,
                button = TimerViewModel.State.Button.Cancel(10),
            ),
        )
    }

    @Test
    fun `WHEN timer is not active THEN emit not active state`() = runTest {
        coEvery { performanceTimer.state }.coAnswers { flowOf(PerformanceTimer.State.NotStarted) }

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

    private fun createSut() = TimerViewModel(
        timer = performanceTimer,
        getStopTimerActionUseCase = getStopTimerActionUseCase,
    )
}