package pl.krystiankaniowski.performance.stats

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase
import pl.krystiankaniowski.performance.model.Focus
import pl.krystiankaniowski.performance.stats.formatters.DateFormatter
import pl.krystiankaniowski.performance.stats.formatters.DurationTimeFormatter
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class StatsViewModelTest {

    private val getFocusListUseCase: GetFocusListUseCase = mockk()
    private val durationTimeFormatter: DurationTimeFormatter = mockk()
    private val dateFormatter: DateFormatter = mockk()

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
        val date = Clock.System.now()
        val formattedDuration = "5 min"
        val formattedDate = "01.01.2001"
        coEvery { getFocusListUseCase.invoke() } returns listOf(Focus(startDate = date, endDate = date))
        coEvery { durationTimeFormatter.format(any(), any()) } returns formattedDuration
        coEvery { dateFormatter.format(any()) } returns formattedDate

        val sut = createSut()

        Assertions.assertEquals(
            StatsViewModel.State.Loaded(
                items = mapOf(
                    StatsViewModel.State.Loaded.Item.Header(date = formattedDate) to listOf(
                        StatsViewModel.State.Loaded.Item.Focus(duration = formattedDuration),
                    ),
                ),
            ),
            sut.state.value,
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
        dateFormatter = dateFormatter,
    )
}