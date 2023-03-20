package pl.krystiankaniowski.performance.timer

import kotlinx.datetime.Clock
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

class OnFocusEndUseCase @Inject constructor(
    private val timerDatastore: TimerDatastore,
    private val saveFocusUseCase: SaveFocusUseCase,
) {

    suspend operator fun invoke() {

        val end = Clock.System.now()
        // check if can end
        val start = requireNotNull(timerDatastore.dateOfStart)

        // log to database
        saveFocusUseCase(
            Focus(
                startDate = start,
                endDate = end,
            ),
        )

        timerDatastore.dateOfStart = null
        timerDatastore.dateOfEnd = null
    }
}