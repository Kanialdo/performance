package pl.krystiankaniowski.performance.stats

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class StatsViewModelTest {

    private val getFocusListUseCase: GetFocusListUseCase = mockk()
    private val durationTimeFormatter: DurationTimeFormatter = mockk()

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runTest {
        val sut = createSut()

        coVerify { getFocusListUseCase.invoke() }

        Assertions.assertEquals(
            sut.state.value,
            StatsViewModel.State.Loading,
        )
    }

    @Test
    fun `WHEN use case provided data THEN proper state is emitted`() = runTest {
        coEvery { getFocusListUseCase.invoke() }.returns(emptyList())

        val sut = createSut()

        Assertions.assertEquals(
            sut.state.value,
            StatsViewModel.State.Loaded(items = emptyMap()),
        )
    }

    @Test
    fun `WHEN refresh is requested THEN perform reload`() = runTest {
        val sut = createSut()

        sut.onEvent(StatsViewModel.Event.Refresh)

        coVerify(atLeast = 2) { getFocusListUseCase.invoke() }
    }

    private fun createSut() = StatsViewModel(
        getFocusListUseCase = getFocusListUseCase,
        durationTimeFormatter = durationTimeFormatter,
    )
}