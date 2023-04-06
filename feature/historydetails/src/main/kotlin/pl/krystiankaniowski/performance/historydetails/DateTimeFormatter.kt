package pl.krystiankaniowski.performance.historydetails

import kotlinx.datetime.Instant
import javax.inject.Inject

class DateTimeFormatter @Inject constructor() {

    fun formatDateTime(instant: Instant): String = instant.toString()
}