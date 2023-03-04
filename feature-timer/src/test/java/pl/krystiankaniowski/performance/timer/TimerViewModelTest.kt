package pl.krystiankaniowski.performance.timer

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TimerViewModelTest {

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runBlocking {
        val viewModel = TimerViewModel()

        Assertions.assertEquals(
            viewModel.state.value,
            TimerViewModel.State(
                counter = "-",
                isTimerActive = false,
                isStartButtonEnabled = true,
            ),
        )
    }

    @Test
    fun `WHEN start is request THEN timer is active`() = runBlocking {
        val viewModel = TimerViewModel()

        viewModel.onEvent(TimerViewModel.Event.Start)

        Assertions.assertEquals(viewModel.state.value.isTimerActive, true)
    }
}