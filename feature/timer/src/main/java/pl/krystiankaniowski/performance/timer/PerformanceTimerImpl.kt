package pl.krystiankaniowski.performance.timer

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOffDoNotDisturbUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOnDoNotDisturbUseCase
import pl.krystiankaniowski.performance.domain.usecase.notification.StartForegroundServiceUseCase
import pl.krystiankaniowski.performance.domain.usecase.notification.StopForegroundServiceUseCase
import pl.krystiankaniowski.performance.model.Focus
import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerformanceTimerImpl @Inject constructor(
    private val saveFocusUseCase: SaveFocusUseCase,
    private val startForegroundServiceUseCase: StartForegroundServiceUseCase,
    private val stopForegroundServiceUseCase: StopForegroundServiceUseCase,
    private val isDoNotDisturbEnabledUseCase: IsDoNotDisturbEnabledUseCase,
    private val turnOnDoNotDisturbUseCase: TurnOnDoNotDisturbUseCase,
    private val turnOffDoNotDisturbUseCase: TurnOffDoNotDisturbUseCase,
) : PerformanceTimer {

    private val scope = MainScope()

    private val _state = MutableSharedFlow<PerformanceTimer.State>(extraBufferCapacity = 1)
    override val state: Flow<PerformanceTimer.State> = _state

    private var job: Job? = null

    private var startDate: Instant? = null

    init {
        scope.launch {
            _state.emit(PerformanceTimer.State.NotStarted)
        }
    }

    override fun start(seconds: Seconds) {
        job = scope.launch {
            startForegroundServiceUseCase()
            startDate = Clock.System.now()
            if (isDoNotDisturbEnabledUseCase()){
                turnOnDoNotDisturbUseCase()
            }
            (seconds.value - 1 downTo 0)
                .asFlow()
                .onEach { delay(1000) }
                .map { Seconds(it) }
                .onStart { emit(seconds) }
                .conflate()
                .onCompletion {
                    onCompletion()
                }
                .collect {
                    _state.emit(
                        PerformanceTimer.State.Pending(
                            elapsedSeconds = seconds - it,
                            leftSeconds = it,
                        ),
                    )
                }
        }
    }

    override fun stop() {
        job?.cancel(CancellationException())
    }

    private fun onCompletion() {
        scope.launch {
            if (isDoNotDisturbEnabledUseCase()){
                turnOffDoNotDisturbUseCase()
            }
            saveFocusUseCase(
                Focus(
                    startDate = requireNotNull(startDate),
                    endDate = Clock.System.now(),
                ),
            )
            startDate = null
            stopForegroundServiceUseCase()
            _state.emit(PerformanceTimer.State.NotStarted)
        }
    }
}