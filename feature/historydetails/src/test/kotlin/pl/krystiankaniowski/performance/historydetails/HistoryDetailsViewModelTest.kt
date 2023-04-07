package pl.krystiankaniowski.performance.historydetails

import io.mockk.Awaits
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.stats.GetHistoryEntryUseCase
import pl.krystiankaniowski.performance.model.Focus
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension
import kotlin.time.Duration.Companion.seconds

@ExtendWith(InstantDispatcherExtension::class)
class HistoryDetailsViewModelTest {

    private val getHistoryEntryUseCase: GetHistoryEntryUseCase = mockk()
    private val dateTimeFormatter: DateTimeFormatter = mockk()

    @Test
    fun `WHEN view model is created THEN emit loading state`() = runTest {
        coEvery { getHistoryEntryUseCase.invoke(any()) } just Awaits

        val sut = createSut(id = 1)

        Assertions.assertEquals(HistoryDetailsViewModel.State.Loading, sut.state.value)
    }

    @Test
    fun `WHEN view model receives data THEN emit loaded state`() = runTest {
        val start = Clock.System.now()
        val end = start.plus(15.seconds)
        val focus = Focus(
            id = 1,
            startDate = start,
            endDate = end,
        )
        coEvery { getHistoryEntryUseCase.invoke(focus.id) } returns focus
        coEvery { dateTimeFormatter.formatDateTime(start) } returns "start"
        coEvery { dateTimeFormatter.formatDateTime(end) } returns "end"

        val sut = createSut(id = focus.id)

        Assertions.assertEquals(
            HistoryDetailsViewModel.State.Loaded(
                startDate = "start",
                endDate = "end",
            ),
            sut.state.value,
        )
    }

    private fun createSut(id: Long) = HistoryDetailsViewModel(
        getHistoryEntryUseCase = getHistoryEntryUseCase,
        dateTimeFormatter = dateTimeFormatter,
        id = id,
    )
}