package pl.krystiankaniowski.performance.stats

import kotlinx.datetime.Instant
import javax.inject.Inject

class DurationTimeFormatter @Inject constructor() {

    fun format(from: Instant, to: Instant): String {
        val diff = (to - from).inWholeSeconds
        return when {
            diff < 60 -> "${diff}s"
            diff < 5 * 60 -> "${diff / 60}m ${diff % 60} s"
            diff < 60 * 60 -> "${diff / 60}m"
            else -> "${diff / 60 * 60}h ${(diff % 60 * 60) / 60}m"
        }
    }
}