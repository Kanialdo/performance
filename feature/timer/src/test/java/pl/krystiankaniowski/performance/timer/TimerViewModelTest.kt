package pl.krystiankaniowski.performance.timer

import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase

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

    private val saveFocusUseCase: SaveFocusUseCase = mockk()

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runTest {
        val viewModel = TimerViewModel(saveFocusUseCase)

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
    fun `WHEN start is requested THEN timer is active`() = runTest {
        val viewModel = TimerViewModel(saveFocusUseCase)

        viewModel.onEvent(TimerViewModel.Event.Start)

        Assertions.assertEquals(viewModel.state.value.isTimerActive, true)
        Assertions.assertEquals(viewModel.state.value.isStartButtonEnabled, false)
        Assertions.assertEquals(viewModel.state.value.isStopButtonEnabled, true)
    }

    @Test
    fun `WHEN stop is requested during pending timer THEN timer is not active and start button enabled`() = runTest {
        val viewModel = TimerViewModel(saveFocusUseCase)

        viewModel.onEvent(TimerViewModel.Event.Start)
        delay(5000)
        viewModel.onEvent(TimerViewModel.Event.Stop)

        Assertions.assertEquals(viewModel.state.value.isTimerActive, false)
        Assertions.assertEquals(viewModel.state.value.isStartButtonEnabled, true)
        Assertions.assertEquals(viewModel.state.value.isStopButtonEnabled, false)
    }
}