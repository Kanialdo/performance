package pl.krystiankaniowski.performance.localization.time

import pl.krystiankaniowski.performance.domain.localization.time.DurationFormatter
import javax.inject.Inject
import kotlin.time.Duration

class DurationFormatterImpl @Inject constructor() : DurationFormatter {

    override fun format(duration: Duration): String {
        val diff = duration.inWholeSeconds
        return when {
            diff < 60 -> "${diff}s"
            diff < 5 * 60 -> "${diff / 60}m" + ((diff % 60).takeIf { it != 0L }?.let { " ${it}s" } ?: "")
            diff < 60 * 60 -> "${diff / 60}m"
            else -> "${diff / 3600}h" + (((diff % 3600) / 60).takeIf { it != 0L }?.let { " ${it}m" } ?: "")
        }
    }
}