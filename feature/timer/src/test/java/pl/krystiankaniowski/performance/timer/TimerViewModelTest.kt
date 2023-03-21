package pl.krystiankaniowski.performance.timer

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class TimerViewModelTest {

    private val performanceTimer: PerformanceTimer = mockk()

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runTest {
        val viewModel = TimerViewModel(performanceTimer)

        Assertions.assertEquals(
            viewModel.state.value,
            TimerViewModel.State(
                counter = "25:00",
                isTimerActive = false,
                isStartButtonEnabled = true,
                isStopButtonEnabled = false,
            ),
        )
    }

    @Test
    fun `WHEN start is requested THEN start is performed on timer`() = runTest {
        val viewModel = TimerViewModel(performanceTimer)

        viewModel.onEvent(TimerViewModel.Event.Start)

        coVerify { performanceTimer.start(any()) }

//        Assertions.assertEquals(viewModel.state.value.isTimerActive, true)
//        Assertions.assertEquals(viewModel.state.value.isStartButtonEnabled, false)
//        Assertions.assertEquals(viewModel.state.value.isStopButtonEnabled, true)
    }

    @Test
    fun `WHEN stop is requested THEN stop is performed on timer`() = runTest {
        val viewModel = TimerViewModel(performanceTimer)

        viewModel.onEvent(TimerViewModel.Event.Stop)

        coVerify { performanceTimer.stop() }
    }

//         viewModel.onEvent(TimerViewModel.Event.Start)
//        delay(5000)
//        viewModel.onEvent(TimerViewModel.Event.Stop)
//
//        Assertions.assertEquals(viewModel.state.value.isTimerActive, false)
//        Assertions.assertEquals(viewModel.state.value.isStartButtonEnabled, true)
//        Assertions.assertEquals(viewModel.state.value.isStopButtonEnabled, false)
}