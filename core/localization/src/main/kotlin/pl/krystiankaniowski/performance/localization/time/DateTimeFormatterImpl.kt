package pl.krystiankaniowski.performance.localization.time

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import pl.krystiankaniowski.performance.domain.localization.DateTimeFormatter
import javax.inject.Inject
import kotlin.time.Duration

class DateTimeFormatterImpl @Inject constructor() : DateTimeFormatter {

    override fun format(instant: Instant, format: DateTimeFormatter.Format): String = when (format) {
        DateTimeFormatter.Format.TIME -> instant.toLocalDateTime(TimeZone.currentSystemDefault()).time.toString()
        DateTimeFormatter.Format.DATE -> instant.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
        DateTimeFormatter.Format.DATE_TIME -> instant.toLocalDateTime(TimeZone.currentSystemDefault()).toString()
    }

    override fun format(duration: Duration): String {
        val diff = duration.inWholeSeconds
        return when {
            diff < 60 -> "${diff}s"
            diff < 5 * 60 -> "${diff / 60}m" + ((diff % 60).takeIf { it != 0L }?.let { " ${it}s" } ?: "")
            diff < 60 * 60 -> "${diff / 60}m"
            else -> "${diff / 3600}h" + (((diff % 3600) / 60).takeIf { it != 0L }?.let { " ${it}m" } ?: "")
        }
    }

    override fun formatTimer(seconds: Long) = "${seconds / 60}:${(seconds % 60).toString().padStart(2, '0')}"
}