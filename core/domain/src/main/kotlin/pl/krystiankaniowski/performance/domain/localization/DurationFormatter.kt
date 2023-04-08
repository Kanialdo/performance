package pl.krystiankaniowski.performance.domain.localization

import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.model.Seconds
import java.text.Format
import kotlin.time.Duration

interface DurationFormatter {

    fun format(instant: Instant, format: Format): String

    fun format(from: Instant, to: Instant): String = format(to - from)

    fun format(duration: Duration): String

    fun formatTimer(seconds: Seconds): String = formatTimer(seconds.value)

    fun formatTimer(seconds: Long): String

    enum class Format {
        TIME,
        DATE,
        DATE_TIME,
    }
}