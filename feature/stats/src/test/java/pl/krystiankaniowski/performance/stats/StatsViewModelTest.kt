package pl.krystiankaniowski.performance.stats

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase

class StatsViewModelTest {

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

    private val getFocusListUseCase: GetFocusListUseCase = mockk()

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runTest {
        val viewModel = StatsViewModel(getFocusListUseCase)

        coVerify { getFocusListUseCase.invoke() }

        Assertions.assertEquals(
            viewModel.state.value,
            StatsViewModel.State.Loading,
        )
    }

    @Test
    fun `WHEN use case provided data THEN proper state is emitted`() = runTest {
        coEvery { getFocusListUseCase.invoke() }.returns(emptyList())

        val viewModel = StatsViewModel(getFocusListUseCase)

        Assertions.assertEquals(
            viewModel.state.value,
            StatsViewModel.State.Loaded(items = emptyList()),
        )
    }

    @Test
    fun `WHEN refresh is requested THEN perform reload`() = runTest {
        val viewModel = StatsViewModel(getFocusListUseCase)

        viewModel.onEvent(StatsViewModel.Event.Refresh)

        coVerify(atLeast = 2) { getFocusListUseCase.invoke() }
    }
}