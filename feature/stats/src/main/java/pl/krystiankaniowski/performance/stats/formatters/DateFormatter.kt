package pl.krystiankaniowski.performance.stats.formatters

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class DateFormatter @Inject constructor() {

    fun format(value: Instant): String = value.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
}