package pl.krystiankaniowski.performance.stats

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.localization.time.DateTimeFormatter
import pl.krystiankaniowski.performance.domain.localization.time.DurationFormatter
import pl.krystiankaniowski.performance.domain.stats.GetFocusListUseCase
import pl.krystiankaniowski.performance.model.Focus
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class StatsViewModelTest {

    private val getFocusListUseCase: GetFocusListUseCase = mockk()
    private val durationFormatter: DurationFormatter = mockk()
    private val dateTimeFormatter: DateTimeFormatter = mockk()

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
    fun `WHEN use case provided data THEN loaded state is emitted`() = runTest {
        val date = Clock.System.now()
        val formattedDuration = "5 min"
        val formattedDate = "01.01.2001"
        coEvery { getFocusListUseCase.invoke() } returns flowOf(listOf(Focus(id = 1, startDate = date, endDate = date)))
        coEvery { durationFormatter.format(any(), any()) } returns formattedDuration
        coEvery { dateTimeFormatter.formatDate(any()) } returns formattedDate

        val sut = createSut()

        Assertions.assertEquals(
            StatsViewModel.State.Loaded(
                items = mapOf(
                    StatsViewModel.State.Loaded.Item.Header(date = formattedDate) to listOf(
                        StatsViewModel.State.Loaded.Item.Focus(id = 1, duration = formattedDuration),
                    ),
                ),
            ),
            sut.state.value,
        )
    }

    @Test
    fun `WHEN use case did not provide data THEN empty state is emitted`() = runTest {
        coEvery { getFocusListUseCase.invoke() } returns flowOf(emptyList())

        val sut = createSut()

        Assertions.assertEquals(
            StatsViewModel.State.Empty,
            sut.state.value,
        )
    }

    @Test
    fun `WHEN add item is clicked THEN emit open add item screen event`() = runTest {
        val sut = createSut()

        sut.effects.test {
            sut.onEvent(StatsViewModel.Event.OnAddItemClick)
            Assertions.assertEquals(StatsViewModel.Effect.OpenAddItem, awaitItem())
        }
    }

    private fun createSut() = StatsViewModel(
        getFocusListUseCase = getFocusListUseCase,
        durationFormatter = durationFormatter,
        dateTimeFormatter = dateTimeFormatter,
    )
}