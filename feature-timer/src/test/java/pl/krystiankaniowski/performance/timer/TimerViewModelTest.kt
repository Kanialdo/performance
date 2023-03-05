package pl.krystiankaniowski.performance.timer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TimerViewModelTest {

    @Suppress("UnusedPrivateMember")
    @BeforeEach
    private fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Suppress("UnusedPrivateMember")
    @AfterEach
    private fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runBlocking {
        val viewModel = TimerViewModel()

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
    fun `WHEN start is requested THEN timer is active`() = runBlocking {
        val viewModel = TimerViewModel()

        viewModel.onEvent(TimerViewModel.Event.Start)

        Assertions.assertEquals(viewModel.state.value.isTimerActive, true)
        Assertions.assertEquals(viewModel.state.value.isStartButtonEnabled, false)
        Assertions.assertEquals(viewModel.state.value.isStopButtonEnabled, true)
    }

    @Test
    fun `WHEN stop is requested during pending timer THEN timer is not active and start button enabled`() = runBlocking {
        val viewModel = TimerViewModel()

        viewModel.onEvent(TimerViewModel.Event.Start)
        delay(5000)
        viewModel.onEvent(TimerViewModel.Event.Stop)

        Assertions.assertEquals(viewModel.state.value.isTimerActive, false)
        Assertions.assertEquals(viewModel.state.value.isStartButtonEnabled, true)
        Assertions.assertEquals(viewModel.state.value.isStopButtonEnabled, false)
    }
}