package pl.krystiankaniowski.performance.historydetails

import kotlinx.datetime.Instant

class DateTimeFormatter {

    fun formatDateTime(instant: Instant): String = instant.toString()
}