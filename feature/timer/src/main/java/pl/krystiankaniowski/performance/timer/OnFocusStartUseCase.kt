package pl.krystiankaniowski.performance.timer

import kotlinx.datetime.Clock
import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject

class OnFocusStartUseCase @Inject constructor(
    private val timerDatastore: TimerDatastore,
) {

    suspend operator fun invoke(seconds: Seconds) {

        // check if can start
        check(timerDatastore.dateOfStart == null)

        // save date of start and end
        val now = Clock.System.now()
        timerDatastore.dateOfStart = now
        timerDatastore.dateOfEnd = now.plus(seconds.asDuration)

        // set alarm
    }
}