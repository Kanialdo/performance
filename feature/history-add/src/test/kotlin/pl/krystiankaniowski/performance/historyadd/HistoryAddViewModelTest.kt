package pl.krystiankaniowski.performance.historyadd

import app.cash.turbine.test
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class HistoryAddViewModelTest {

    private val repository: FocusRepository = mockk()

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runTest {
        val sut = createSut()

        Assertions.assertEquals(
            sut.state.value,
            HistoryAddViewModel.State(
                startDate = null,
                startTime = null,
                endDate = null,
                endTime = null,
            ),
        )
    }

    @Test
    fun `WHEN select date and time event are invoked THEN proper state is emitted`() = runTest {
        val startDate = LocalDate(2022, 10, 22)
        val startTime = LocalTime(10, 15)
        val endDate = LocalDate(2022, 10, 22)
        val endTime = LocalTime(10, 40)

        val sut = createSut()

        Assertions.assertEquals(
            sut.state.value,
            HistoryAddViewModel.State(startDate = null, startTime = null, endDate = null, endTime = null),
        )

        sut.onEvent(HistoryAddViewModel.Event.StartDateChange(startDate))
        Assertions.assertEquals(
            sut.state.value,
            HistoryAddViewModel.State(startDate = startDate, startTime = null, endDate = null, endTime = null),
        )

        sut.onEvent(HistoryAddViewModel.Event.StartTimeChange(startTime))
        Assertions.assertEquals(
            sut.state.value,
            HistoryAddViewModel.State(startDate = startDate, startTime = startTime, endDate = null, endTime = null),
        )

        sut.onEvent(HistoryAddViewModel.Event.EndDateChange(endDate))
        Assertions.assertEquals(
            sut.state.value,
            HistoryAddViewModel.State(startDate = startDate, startTime = startTime, endDate = endDate, endTime = null),
        )

        sut.onEvent(HistoryAddViewModel.Event.EndTimeChange(endTime))
        Assertions.assertEquals(
            sut.state.value,
            HistoryAddViewModel.State(startDate = startDate, startTime = startTime, endDate = endDate, endTime = endTime),
        )
    }

    @Test
    fun `WHEN data is improperly filled THEN save button is not enabled`() = runTest {
        coEvery { repository.add(any()) } just Runs

        val sut = createSut()
        sut.selectAllValues(
            startDate = LocalDate(2022, 10, 22),
            startTime = LocalTime(10, 15),
            endDate = LocalDate(2022, 10, 22),
            endTime = LocalTime(10, 14),
        )

        Assertions.assertFalse(sut.state.value.isSaveButtonEnable)
    }

    @Test
    fun `WHEN data is properly filled and save button is clicked when form is filled THEN save session and emit close event`() = runTest {
        coEvery { repository.add(any()) } just Runs

        val sut = createSut()
        sut.selectAllValues(
            startDate = LocalDate(2022, 10, 22),
            startTime = LocalTime(10, 15),
            endDate = LocalDate(2022, 10, 22),
            endTime = LocalTime(10, 40),
        )

        Assertions.assertTrue(sut.state.value.isSaveButtonEnable)

        sut.effects.test {
            sut.onEvent(HistoryAddViewModel.Event.OnSaveClick)
            Assertions.assertEquals(HistoryAddViewModel.Effect.CloseScreen, awaitItem())
        }

        coVerify { repository.add(any()) }
    }

    @Test
    fun `WHEN save button is clicked when is disabled THEN do not save anything`() = runTest {
        val sut = createSut()

        sut.onEvent(HistoryAddViewModel.Event.OnSaveClick)

        coVerify(exactly = 0) { repository.add(any()) }
    }

    private fun createSut() = HistoryAddViewModel(
        repository = repository,
    )

    private fun HistoryAddViewModel.selectAllValues(
        startDate: LocalDate?,
        startTime: LocalTime?,
        endDate: LocalDate?,
        endTime: LocalTime?,
    ) {
        onEvent(HistoryAddViewModel.Event.StartDateChange(startDate))
        onEvent(HistoryAddViewModel.Event.StartTimeChange(startTime))
        onEvent(HistoryAddViewModel.Event.EndDateChange(endDate))
        onEvent(HistoryAddViewModel.Event.EndTimeChange(endTime))
    }
}