package pl.krystiankaniowski.performance.infrastructure

import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.domain.localization.DateTimeFormatter
import javax.inject.Inject

class DateTimeFormatterImpl @Inject constructor() : DateTimeFormatter {
    override fun format(instant: Instant, format: DateTimeFormatter.Format) {
        TODO("Not yet implemented")
    }
}