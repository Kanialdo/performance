package pl.krystiankaniowski.performance.timer

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOffDoNotDisturbUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOnDoNotDisturbUseCase
import pl.krystiankaniowski.performance.domain.usecase.notification.StartForegroundServiceUseCase
import pl.krystiankaniowski.performance.domain.usecase.notification.StopForegroundServiceUseCase
import pl.krystiankaniowski.performance.model.Seconds
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class PerformanceTimerImplTest {

    private val saveFocusUseCase: SaveFocusUseCase = mockk()
    private val startForegroundServiceUseCase: StartForegroundServiceUseCase = mockk()
    private val stopForegroundServiceUseCase: StopForegroundServiceUseCase = mockk()
    private val isDoNotDisturbEnabledUseCase: IsDoNotDisturbEnabledUseCase = mockk()
    private val turnOnDoNotDisturbUseCase: TurnOnDoNotDisturbUseCase = mockk()
    private val turnOffDoNotDisturbUseCase: TurnOffDoNotDisturbUseCase = mockk()

    @Test
    fun test() = runTest {

        coEvery { saveFocusUseCase.invoke(any()) }.answers { }
        coEvery { startForegroundServiceUseCase.invoke() }.answers { }
        coEvery { stopForegroundServiceUseCase.invoke() }.answers { }
        coEvery { isDoNotDisturbEnabledUseCase.invoke() }.returns(true)
        coEvery { turnOnDoNotDisturbUseCase.invoke() }.answers { }
        coEvery { turnOffDoNotDisturbUseCase.invoke() }.answers { }

        val sut = createSut()

        sut.state.test {
            sut.start(Seconds(5))
            Assertions.assertEquals(PerformanceTimer.State.Pending(elapsedSeconds = Seconds(0), leftSeconds = Seconds(5)), awaitItem())
            Assertions.assertEquals(PerformanceTimer.State.Pending(elapsedSeconds = Seconds(1), leftSeconds = Seconds(4)), awaitItem())
            Assertions.assertEquals(PerformanceTimer.State.Pending(elapsedSeconds = Seconds(2), leftSeconds = Seconds(3)), awaitItem())
            Assertions.assertEquals(PerformanceTimer.State.Pending(elapsedSeconds = Seconds(3), leftSeconds = Seconds(2)), awaitItem())
            Assertions.assertEquals(PerformanceTimer.State.Pending(elapsedSeconds = Seconds(4), leftSeconds = Seconds(1)), awaitItem())
            Assertions.assertEquals(PerformanceTimer.State.NotStarted, awaitItem())
        }
    }

    private fun createSut() = PerformanceTimerImpl(
        saveFocusUseCase = saveFocusUseCase,
        startForegroundServiceUseCase = startForegroundServiceUseCase,
        stopForegroundServiceUseCase = stopForegroundServiceUseCase,
        isDoNotDisturbEnabledUseCase = isDoNotDisturbEnabledUseCase,
        turnOnDoNotDisturbUseCase = turnOnDoNotDisturbUseCase,
        turnOffDoNotDisturbUseCase = turnOffDoNotDisturbUseCase,
    )

}