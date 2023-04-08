package pl.krystiankaniowski.performance.domain.localization

import kotlinx.datetime.Instant
import java.text.Format

interface DateTimeFormatter {

    fun format(instant: Instant, format: Format)

    enum class Format {
        TIME,
        DATE,
        DATE_TIME,
    }
}