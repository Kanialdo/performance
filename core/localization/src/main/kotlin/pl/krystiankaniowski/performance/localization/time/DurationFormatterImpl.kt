package pl.krystiankaniowski.performance.localization.time

import pl.krystiankaniowski.performance.domain.localization.time.DurationFormatter
import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject

class DurationFormatterImpl @Inject constructor() : DurationFormatter {

    override fun format(duration: Seconds): String {
        val diff = duration.value
        return when {
            diff < 60 -> "${diff}s"
            diff < 5 * 60 -> "${diff / 60}m" + ((diff % 60).takeIf { it != 0L }?.let { " ${it}s" } ?: "")
            diff < 60 * 60 -> "${diff / 60}m"
            else -> "${diff / 3600}h" + (((diff % 3600) / 60).takeIf { it != 0L }?.let { " ${it}m" } ?: "")
        }
    }
}