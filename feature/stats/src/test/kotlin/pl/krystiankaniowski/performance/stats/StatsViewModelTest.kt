package pl.krystiankaniowski.performance.stats

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.localization.time.DateTimeFormatter
import pl.krystiankaniowski.performance.domain.localization.time.DurationFormatter
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import pl.krystiankaniowski.performance.model.Focus
import pl.krystiankaniowski.performance.model.Seconds
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class StatsViewModelTest {

    private val repository: FocusRepository = mockk()
    private val dateTimeFormatter: DateTimeFormatter = mockk()
    private val durationFormatter: DurationFormatter = mockk()

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runTest {
        coEvery { repository.getAll(any(), any()) } returns emptyFlow()

        val sut = createSut()

        Assertions.assertEquals(
            sut.state.value,
            StatsViewModel.State.Loading,
        )
    }

    @Test
    fun `WHEN no data is returned from repository THEN proper state is emitted`() = runTest {
        val date = "2000-01-01"
        val formattedTime = "0 min"
        coEvery { repository.getAll(any(), any()) } returns flowOf(emptyList())
        coEvery { dateTimeFormatter.formatDate(any<LocalDate>()) } returns date
        coEvery { durationFormatter.format(any<Seconds>()) } returns formattedTime

        val sut = createSut()

        Assertions.assertEquals(
            sut.state.value,
            StatsViewModel.State.Daily(
                date = date,
                total = formattedTime,
                chartData = emptyList(),
            ),
        )
    }

    @Test
    fun `WHEN data is returned from repository THEN proper state is emitted`() = runTest {
        val item = Focus(
            id = -1,
            startDate = LocalDateTime(2000, 1, 1, 15, 0, 0).toInstant(TimeZone.currentSystemDefault()),
            endDate = LocalDateTime(2000, 1, 1, 16, 0, 0).toInstant(TimeZone.currentSystemDefault()),
        )
        val date = "2000-01-01"
        val formattedTime = "0 min"
        coEvery { repository.getAll(any(), any()) } returns flowOf(listOf(item))
        coEvery { dateTimeFormatter.formatDate(any<LocalDate>()) } returns date
        coEvery { durationFormatter.format(any<Seconds>()) } returns formattedTime

        val sut = createSut()

        Assertions.assertEquals(
            sut.state.value,
            StatsViewModel.State.Daily(
                date = date,
                total = formattedTime,
                chartData = listOf(
                    StatsViewModel.FocusTime(
                        millsStart = LocalDateTime(2000, 1, 1, 15, 0, 0).time.toMillisecondOfDay().toLong(),
                        millsEnd = LocalDateTime(2000, 1, 1, 16, 0, 0).time.toMillisecondOfDay().toLong(),
                    ),
                ),
            ),
        )
    }

    @Test
    fun `WHEN exception is returned THEN proper state is emitted`() = runTest {
        coEvery { repository.getAll(any(), any()) } throws Exception()

        val sut = createSut()

        Assertions.assertEquals(
            sut.state.value,
            StatsViewModel.State.Loading,
        )
    }

    private fun createSut() = StatsViewModel(
        repository = repository,
        dateTimeFormatter = dateTimeFormatter,
        durationFormatter = durationFormatter,
    )
}