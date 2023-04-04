package pl.krystiankaniowski.performance.timer

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.model.Seconds
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class PerformanceTimerImplTest {

    private val timerDuration = 5L

    @Test
    fun `WHEN start is called THEN all statuses are emitted`() = runTest {

        val sut = createSut()

        sut.state.test {
            Assertions.assertEquals(PerformanceTimer.State.NotStarted, awaitItem())
            sut.start(Seconds(timerDuration))
            for (i in 0..timerDuration) {
                Assertions.assertEquals(PerformanceTimer.State.Pending(elapsedSeconds = Seconds(i), leftSeconds = Seconds(timerDuration - i)), awaitItem())
            }
            Assertions.assertEquals(PerformanceTimer.State.NotStarted, awaitItem())
        }
    }

    @Test
    fun `WHEN stop is called during active timer THEN future statuses are not emitted`() = runTest {

        val sut = createSut()

        sut.state.test {
            Assertions.assertEquals(PerformanceTimer.State.NotStarted, awaitItem())
            sut.start(Seconds(timerDuration))
            Assertions.assertEquals(PerformanceTimer.State.Pending(elapsedSeconds = Seconds(0), leftSeconds = Seconds(5)), awaitItem())
            sut.stop()
            Assertions.assertEquals(PerformanceTimer.State.NotStarted, awaitItem())
        }
    }

    @Test
    fun `WHEN start and stop are called THEN observers are notified`() = runTest {

        val observer1: TimerObserver = mockk<TimerObserver>().apply {
            every { priority }.returns(1)
            coEvery { onStart() }.coAnswers { }
            coEvery { onStop(any()) }.coAnswers { }
        }
        val observer2: TimerObserver = mockk<TimerObserver>().apply {
            every { priority }.returns(2)
            coEvery { onStart() }.coAnswers { }
            coEvery { onStop(any()) }.coAnswers { }
        }

        val sut = createSut(
            observers = setOf(observer1, observer2),
        )

        sut.start(Seconds(timerDuration))
        coVerify { observer1.onStart() }
        coVerify { observer2.onStart() }

        sut.stop()
        coVerify { observer1.onStop(any()) }
        coVerify { observer2.onStop(any()) }
    }

    @Test
    fun `WHEN cancel is called THEN observers are notified`() = runTest {
        TODO()
    }

    private fun createSut(
        observers: Set<TimerObserver> = emptySet(),
    ) = PerformanceTimerImpl(
        observers = observers,
    )
}