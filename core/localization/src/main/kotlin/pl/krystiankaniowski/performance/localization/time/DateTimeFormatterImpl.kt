package pl.krystiankaniowski.performance.localization.time

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import pl.krystiankaniowski.performance.domain.localization.time.DateTimeFormatter
import javax.inject.Inject

class DateTimeFormatterImpl @Inject constructor() : DateTimeFormatter {

    private val timeZone = TimeZone.currentSystemDefault()

    override fun formatDate(date: LocalDate) = date.toString()

    override fun formatDate(instant: Instant) = instant.toLocalDateTime(timeZone).date.toString()

    override fun formatTime(instant: Instant) = instant.toLocalDateTime(timeZone).time.toString()

    override fun formatDateTime(instant: Instant) = instant.toLocalDateTime(timeZone).toString()
}