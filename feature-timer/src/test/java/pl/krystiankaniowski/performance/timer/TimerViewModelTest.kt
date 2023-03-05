package pl.krystiankaniowski.performance.timer

import kotlinx.coroutines.Dispatchers
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