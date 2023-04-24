package pl.krystiankaniowski.performance.history.usecase

import kotlinx.datetime.Clock
import pl.krystiankaniowski.performance.domain.stats.SaveFocusUseCase
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class GenerateHistoryUseCase @Inject constructor(
    private val saveFocusUseCase: SaveFocusUseCase,
) {

    private val amount = 180

    suspend operator fun invoke() {
        val random = Random(System.currentTimeMillis())
        val items = buildList(capacity = amount) {
            for (i in 0 until amount) {
                val endDate = Clock.System.now().minus(random.nextInt((3 * 30)).days).minus(random.nextInt((24)).hours).minus(random.nextInt((60)).minutes)
                val startDate = endDate.minus(25.minutes)
                add(Focus(startDate = startDate, endDate = endDate, tag = null))
                // TODO: Random tag generation
            }
        }.sortedBy { it.startDate }

        items.forEach {
            saveFocusUseCase(it)
        }
    }
}