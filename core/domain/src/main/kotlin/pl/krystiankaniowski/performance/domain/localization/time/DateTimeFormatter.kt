package pl.krystiankaniowski.performance.domain.localization.time

import kotlinx.datetime.Instant

interface DateTimeFormatter {

    fun formatDate(instant: Instant): String
    fun formatTime(instant: Instant): String
    fun formatDateTime(instant: Instant): String
}